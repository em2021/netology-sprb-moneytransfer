package ru.netology.moneytransfer.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.moneytransfer.model.Amount;
import ru.netology.moneytransfer.model.Card;
import ru.netology.moneytransfer.model.TransferData;

import java.util.stream.Stream;

public class CardValidationServiceImplTests {

    private CardValidationService cardValidationServiceImpl;

    @BeforeAll
    static void beforeAll() {
        System.out.println("CardValidationServiceImplTests tests started");
    }

    @BeforeEach
    void setUp() {
        cardValidationServiceImpl = new CardValidationServiceImpl();
        System.out.println("CardValidationServiceImplTests test started");
    }

    @AfterEach
    void tearDown() {
        System.out.println("CardValidationServiceImplTests test completed");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("CardValidationServiceImplTests tests completed");
    }

    @ParameterizedTest
    @MethodSource("sourceForCardIsValidTest")
    public void testCardIsValid_whenSomeConditionIsFalse_throwException(TransferData transferData,
                                                                        Card card,
                                                                        boolean expected) {
        //when:
        //then:
        Assertions.assertThrows(RuntimeException.class,
                () -> cardValidationServiceImpl.cardIsValid(transferData, card));
    }

    @Test
    public void testCardsDiffer_whenSame_throwException() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1111111111111111",
                "12/23",
                "123",
                "1111111111111111",
                amount);
        //when:
        //then:
        Assertions.assertThrows(RuntimeException.class,
                () -> cardValidationServiceImpl.cardsDiffer(transferData));
    }

    @Test
    public void testCardExists_whenDoesnExist_throwException() {
        //given:
        Card card = null;
        //when:
        //then:
        Assertions.assertThrows(RuntimeException.class,
                () -> cardValidationServiceImpl.cardExists(card));
    }

    @Test
    public void testExpirationDateMatches_whenDiffers_throwException() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1111111111111111",
                "12/24",
                "123",
                "1111111111111111",
                amount);
        Card card = new Card("1111111111111111", "12", "2023", "123");
        //when:
        //then:
        Assertions.assertThrows(RuntimeException.class,
                () -> cardValidationServiceImpl.expirationDateMatches(transferData, card));
    }

    @Test
    public void testCvvMatches_whenDiffers_throwException() {
        //given:
        Amount amount = new Amount(100, "RUR");
        TransferData transferData = new TransferData("1111111111111111",
                "12/23",
                "124",
                "1111111111111111",
                amount);
        Card card = new Card("1111111111111111", "12", "2023", "123");
        //when:
        //then:
        Assertions.assertThrows(RuntimeException.class,
                () -> cardValidationServiceImpl.cvvMatches(transferData, card));
    }

    public static Stream<Arguments> sourceForCardIsValidTest() {
        //given:
        Amount amount = new Amount(100, "RUR");
        Card card = new Card("1111111111111111", "12", "2023", "123");
        return Stream.of(Arguments.of(new TransferData("1111111111111111",
                                "12/23",
                                "123",
                                "1111111111111111",
                                amount),
                        card,
                        false),
                Arguments.of(new TransferData("3333333333333333",
                                "12/24",
                                "123",
                                "2222222222222222",
                                amount),
                        card,
                        false),
                Arguments.of(new TransferData("1111111111111111",
                                "12/24",
                                "123",
                                "2222222222222222",
                                amount),
                        card,
                        false),
                Arguments.of(new TransferData("1111111111111111",
                                "12/23",
                                "124",
                                "2222222222222222",
                                amount),
                        card,
                        false));
    }
}