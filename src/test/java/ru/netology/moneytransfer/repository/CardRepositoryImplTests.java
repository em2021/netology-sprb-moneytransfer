package ru.netology.moneytransfer.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class CardRepositoryImplTests {

    private CardRepository cardRepositoryImpl;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("CardRepositoryImplTests tests started");
    }

    @BeforeEach
    public void setUp() {
        cardRepositoryImpl = new CardRepositoryImpl();
        System.out.println("CardRepositoryImplTests test started");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("CardRepositoryImplTests test completed");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("CardRepositoryImplTests tests completed");
    }

    @ParameterizedTest
    @MethodSource("sourceForGetCardTest")
    public void testGetCard_whenValidCard_returnTrue_elseFalse(String string, boolean expected) {
        //when:
        boolean actual = cardRepositoryImpl.getCard(string) != null;
        //then:
        Assertions.assertEquals(expected, actual);
    }

    public static Stream<Arguments> sourceForGetCardTest() {
        //given:
        return Stream.of(Arguments.of("1111111111111111", true),
                Arguments.of("2222222222222222", true),
                Arguments.of("3333333333333333", false));
    }
}