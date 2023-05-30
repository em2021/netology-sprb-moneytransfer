package ru.netology.moneytransfer.service;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.netology.moneytransfer.exception.InvalidInput;
import ru.netology.moneytransfer.exception.TransferNotConfirmed;
import ru.netology.moneytransfer.model.Amount;
import ru.netology.moneytransfer.model.Card;
import ru.netology.moneytransfer.model.TransferData;
import ru.netology.moneytransfer.repository.CardRepository;

public class MoneyTransferServiceTests {

    @InjectMocks
    private MoneyTransferService moneyTransferService;
    @Mock
    CardRepository cardRepository;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("MoneyTransferServiceTests tests started");
    }

    //TODO: Fix test setup
    @BeforeEach
    public void setUp() {
        moneyTransferService = new MoneyTransferService();
        MockitoAnnotations.openMocks(this);
        System.out.println("MoneyTransferServiceTests test started");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("MoneyTransferServiceTests test completed");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("MoneyTransferServiceTests tests completed");
    }

    @Test
    public void testMakeTransfer_whenSameCards_throwException() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1234123412341234",
                "12/23",
                "123",
                "1234123412341234",
                amount);
        //then:
        Assertions.assertThrows(TransferNotConfirmed.class, () -> {
            //when:
            moneyTransferService.makeTransfer(transferData);
        });
    }

    @Test
    public void testMakeTransfer_whenCardDoesntExist_throwException() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1234123412341234",
                "12/23",
                "123",
                "4567456745674567",
                amount);
        Mockito.when(cardRepository.getCard(transferData.getCardFromNumber()))
                .thenReturn(null);
        //then:
        Assertions.assertThrows(InvalidInput.class, () -> {
            //when:
            moneyTransferService.makeTransfer(transferData);
        });
    }

    @Test
    public void testMakeTransfer_whenExpirationDateDoesntMatch_throwException() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1234123412341234",
                "12/23",
                "123",
                "4567456745674567",
                amount);
        Card card = new Card("1234123412341234", "12", "2024", "123");
        Mockito.when(cardRepository.getCard(transferData.getCardFromNumber()))
                .thenReturn(card);
        //then:
        Assertions.assertThrows(InvalidInput.class, () -> {
            //when:
            moneyTransferService.makeTransfer(transferData);
        });
    }

    @Test
    public void testMakeTransfer_whenCvvDoesntMatch_throwException() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1234123412341234",
                "12/23",
                "123",
                "4567456745674567",
                amount);
        Card card = new Card("1234123412341234", "12", "2023", "124");
        Mockito.when(cardRepository.getCard(transferData.getCardFromNumber()))
                .thenReturn(card);
        //then:
        Assertions.assertThrows(InvalidInput.class, () -> {
            //when:
            moneyTransferService.makeTransfer(transferData);
        });
    }

    @Test
    public void testMakeTransfer_whenCardExistsAndValid_thenDontThrowException() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1234123412341234",
                "12/23",
                "123",
                "4567456745674567",
                amount);
        Card card = new Card("1234123412341234", "12", "2023", "123");
        Mockito.when(cardRepository.getCard(transferData.getCardFromNumber()))
                .thenReturn(card);
        //then:
        Assertions.assertDoesNotThrow(() -> {
            //when:
            moneyTransferService.makeTransfer(transferData);
        });
    }
//TODO: Fix test for new method code
//    @Test
//    public void testConfirmOperation_whenCodeIsNull_thenThrowException() {
//        //given:
//        Code code = Mockito.mock(Code.class);
//        Mockito.when(code.getCode())
//                .thenReturn(null);
//        //then:
//        Assertions.assertThrows(OperationNotConfirmed.class, () -> {
//            //when:
//            moneyTransferService.confirmOperation(code);
//        });
//    }
//TODO: Fix test for new method code
//    @Test
//    public void testConfirmOperation_whenCodeIsNotNull_thenDontThrowException() {
//        //given:
//        Code code = Mockito.mock(Code.class);
//        Mockito.when(code.getCode())
//                .thenReturn("0000");
//        //then:
//        Assertions.assertDoesNotThrow(() -> {
//            //when:
//            moneyTransferService.confirmOperation(code);
//        });
//    }
}
