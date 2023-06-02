package ru.netology.moneytransfer;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.moneytransfer.model.Amount;
import ru.netology.moneytransfer.model.OperationId;
import ru.netology.moneytransfer.model.TransferData;

import java.util.stream.Stream;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferApplicationTests {

    private static TestRestTemplate restTemplate;

    @BeforeAll
    static void beforeAll() {
        restTemplate = new TestRestTemplate();
    }

    @Container
    private static GenericContainer<?> moneyTransferApp = new GenericContainer<>("moneytransferapp")
            .withExposedPorts(5500);

    @ParameterizedTest
    @MethodSource("sourceForTransferEndpointTests")
    public void testTransferEndpoint(HttpEntity<?> request, int expected) {
        //given:
        int port = 5500;
        String endpoint = "/transfer";
        //when:
        ResponseEntity<String> forEntity =
                restTemplate.postForEntity("http://localhost:" + moneyTransferApp.getMappedPort(port) + endpoint,
                        request,
                        String.class);
        //then:
        int actual = forEntity.getStatusCodeValue();
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("sourceForConfirmOperationEndpointTests")
    public void testConfirmOperationEndpoint(String code, String operationId, int expected) throws JSONException {
        //given:
        int port = 5500;
        String endpoint = "/confirmOperation";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject jsonConfirmationData = new JSONObject();
        jsonConfirmationData.accumulate("code", code);
        jsonConfirmationData.accumulate("operationId", operationId);
        HttpEntity<String> confDataRequest = new HttpEntity<>(jsonConfirmationData.toString(), headers);
        //when:
        ResponseEntity<String> response = restTemplate
                .postForEntity("http://localhost:" + moneyTransferApp.getMappedPort(port) + endpoint,
                        confDataRequest,
                        String.class);
        int actual = response.getStatusCodeValue();
        //then:
        Assertions.assertEquals(expected, actual);
    }

    public static Stream<Arguments> sourceForTransferEndpointTests() {
        //given:
        TransferData validTransferData = getValidTransferData();
        TransferData sameCardTransferData = getValidTransferData();
        sameCardTransferData.setCardToNumber("1111111111111111");
        TransferData invalidCardTransferData = getValidTransferData();
        invalidCardTransferData.setCardFromNumber("2222111111111111");
        TransferData invalidExpirationTransferData = getValidTransferData();
        invalidExpirationTransferData.setCardFromValidTill("12/25");
        TransferData invalidCvvTransferData = getValidTransferData();
        invalidCvvTransferData.setCardFromCVV("333");
        return Stream.of(
                //Transfer between valid cards
                Arguments.of(new HttpEntity<>(validTransferData),
                        200),
                //Transfer to the same card
                Arguments.of(new HttpEntity<>(sameCardTransferData),
                        500),
                //Source card doesnt exist
                Arguments.of(new HttpEntity<>(invalidCardTransferData),
                        400),
                //Invalid expiration date
                Arguments.of(new HttpEntity<>(invalidExpirationTransferData),
                        400),
                //Invalid CVV
                Arguments.of(new HttpEntity<>(invalidCvvTransferData),
                        400));
    }

    public static Stream<Arguments> sourceForConfirmOperationEndpointTests() {
        //given:
        String validCode = "0000";
        String nullCode = null;
        String nullOperationId = null;
        return Stream.of(
                //Valid confirmation data
                Arguments.of(validCode, getValidOperationId().getOperationId(), 200),
                //Operation id is null
                Arguments.of(validCode, nullOperationId, 400),
                //Code is null
                Arguments.of(nullCode, getValidOperationId().getOperationId(), 400),
                //Invalid operation id
                Arguments.of(validCode, "invalidOperationId", 500));
    }

    private static OperationId getValidOperationId() {
        int port = 5500;
        TransferData transferData = getValidTransferData();
        return restTemplate.postForObject("http://localhost:"
                        + moneyTransferApp.getMappedPort(port)
                        + "/transfer",
                transferData,
                OperationId.class);
    }

    private static TransferData getValidTransferData() {
        return new TransferData("1111111111111111",
                "12/23",
                "123",
                "2222222222222222",
                new Amount(100, "RUR"));
    }
}