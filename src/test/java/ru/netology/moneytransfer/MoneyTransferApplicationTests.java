package ru.netology.moneytransfer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import ru.netology.moneytransfer.model.Amount;
import ru.netology.moneytransfer.model.Code;
import ru.netology.moneytransfer.model.TransferData;

import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferApplicationTests {

    @Autowired
    TestRestTemplate restTemplate;

    private static GenericContainer<?> moneyTransferApp = new GenericContainer<>("moneytransferapp")
            .withExposedPorts(5500);

    @BeforeAll
    public static void setUp() {
        moneyTransferApp.start();
    }

    @AfterAll
    public static void completed() {
        moneyTransferApp.stop();
    }

    @ParameterizedTest
    @MethodSource("sourceForContextLoads")
    public void contextLoads(HttpEntity<?> request, String endpoint, int expected) {
        //given:
        int port = 5500;
        //when:
        ResponseEntity<String> forEntity =
                restTemplate.postForEntity("http://localhost:" + moneyTransferApp.getMappedPort(port) + endpoint,
                        request,
                        String.class);
        //then:
        int actual = forEntity.getStatusCodeValue();
        Assertions.assertEquals(expected, actual);
    }

    public static Stream<Arguments> sourceForContextLoads() {
        //given:
        return Stream.of(
                Arguments.of(new HttpEntity<>(new TransferData("1111111111111111",
                                "12/23",
                                "123",
                                "2222222222222222",
                                new Amount(100, "RUR"))),
                        "/transfer",
                        200),
                Arguments.of(new HttpEntity<>(new TransferData("1111111111111111",
                                "12/23",
                                "123",
                                "1111111111111111",
                                new Amount(100, "RUR"))),
                        "/transfer",
                        500),
                Arguments.of(new HttpEntity<>(new TransferData("2222111111111111",
                                "12/23",
                                "123",
                                "2222222222222222",
                                new Amount(100, "RUR"))),
                        "/transfer",
                        400),
                Arguments.of(new HttpEntity<>(new TransferData("1111111111111111",
                                "12/25",
                                "123",
                                "2222222222222222",
                                new Amount(100, "RUR"))),
                        "/transfer",
                        400),
                Arguments.of(new HttpEntity<>(new TransferData("1111111111111111",
                                "12/23",
                                "333",
                                "2222222222222222",
                                new Amount(100, "RUR"))),
                        "/transfer",
                        400),
                Arguments.of(new HttpEntity<>(new Code("0000")),
                        "/confirmOperation",
                        200),
                Arguments.of(new HttpEntity<>(new Code()),
                        "/confirmOperation",
                        500));

    }
}
