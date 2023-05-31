package ru.netology.moneytransfer.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.netology.moneytransfer.exception.InvalidInput;
import ru.netology.moneytransfer.exception.OperationNotConfirmed;
import ru.netology.moneytransfer.model.*;
import ru.netology.moneytransfer.repository.CardRepository;
import ru.netology.moneytransfer.repository.OperationIdRepository;

import java.util.stream.Stream;

public class MoneyTransferServiceImplTests {

    private MoneyTransferService moneyTransferServiceImpl;
    @Mock
    private CardRepository cardRepositoryImpl;
    @Mock
    private OperationIdRepository operationIdRepositoryImpl;
    @Mock
    private CardValidationService cardValidationServiceImp;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("MoneyTransferServiceImplTests tests started");
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        moneyTransferServiceImpl = new MoneyTransferServiceImpl(cardRepositoryImpl,
                operationIdRepositoryImpl,
                cardValidationServiceImp);
        System.out.println("MoneyTransferServiceImplTests test started");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("MoneyTransferServiceImplTests test completed");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("MoneyTransferServiceImplTests tests completed");
    }

    @Test
    public void testMakeTransfer_whenCardIsValid_returnOperationId() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1111111111111111",
                "12/23",
                "123",
                "2222222222222222",
                amount);
        Card card = new Card("1111111111111111", "12", "2023", "123");
        Mockito.when(cardRepositoryImpl.getCard(Mockito.startsWith("1")))
                .thenReturn(card);
        Mockito.when(cardValidationServiceImp.cardIsValid(transferData, card))
                .thenReturn(true);
        //when:
        OperationId actual = moneyTransferServiceImpl.makeTransfer(transferData);
        //then:
        Assertions.assertNotNull(actual);
    }

    @ParameterizedTest
    @MethodSource("sourceForConfirmOperation")
    public void testConfirmOperation_whenConfirmationDataOrCodeIsNull_thenThrowException(Code code,
                                                                                         OperationId opId) {
        //given:
        ConfirmationData confirmationData = Mockito.mock(ConfirmationData.class);
        Mockito.when(confirmationData.getOperationId())
                .thenReturn(opId);
        Mockito.when(confirmationData.getCode())
                .thenReturn(code);
        //then:
        Assertions.assertThrows(InvalidInput.class, () -> {
            //when:
            moneyTransferServiceImpl.confirmOperation(confirmationData);
        });
    }

    @Test
    public void testConfirmOperation_whenCodeIsNotNull_thenDontThrowException() {
        //given:
        OperationId opId = new OperationId("111");
        Code code = new Code("0000");
        ConfirmationData confirmationData = Mockito.mock(ConfirmationData.class);
        Mockito.when(confirmationData.getOperationId())
                .thenReturn(opId);
        Mockito.when(confirmationData.getCode())
                .thenReturn(code);
        Mockito.when(operationIdRepositoryImpl.putCode(opId, code))
                .thenReturn(true);
        //then:
        Assertions.assertDoesNotThrow(() -> {
            //when:
            moneyTransferServiceImpl.confirmOperation(confirmationData);
        });
    }

    @Test
    public void testConfirmOperation_whenPutCodeReturnsFalse_thenThrowException() {
        //given:
        OperationId opId = new OperationId("111");
        Code code = new Code("0000");
        ConfirmationData confirmationData = Mockito.mock(ConfirmationData.class);
        Mockito.when(confirmationData.getOperationId())
                .thenReturn(opId);
        Mockito.when(confirmationData.getCode())
                .thenReturn(code);
        Mockito.when(operationIdRepositoryImpl.putCode(opId, code))
                .thenReturn(false);
        //then:
        Assertions.assertThrows(OperationNotConfirmed.class, () -> {
            //when:
            moneyTransferServiceImpl.confirmOperation(confirmationData);
        });
    }

    public static Stream<Arguments> sourceForConfirmOperation() {
        OperationId nullOpId = null;
        Code nullCode = null;
        return Stream.of(Arguments.of(nullCode, new OperationId("1")),
                Arguments.of(new Code("0000"), nullOpId));
    }
}