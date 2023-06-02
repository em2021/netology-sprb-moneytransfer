package ru.netology.moneytransfer.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.moneytransfer.model.Code;
import ru.netology.moneytransfer.model.OperationId;

import java.util.stream.Stream;

public class OperationIdRepositoryImplTests {

    private OperationIdRepository operationIdRepositoryImpl;

    @BeforeAll
    static void beforeAll() {
        System.out.println("OperationIdRepositoryImplTests tests started");
    }

    @BeforeEach
    void setUp() {
        operationIdRepositoryImpl = new OperationIdRepositoryImpl();
        System.out.println("OperationIdRepositoryImplTests test started");
    }

    @AfterEach
    void tearDown() {
        System.out.println("OperationIdRepositoryImplTests test completed");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("OperationIdRepositoryImplTests tests completed");
    }

    @ParameterizedTest
    @MethodSource("sourceForPutOperationIdTest")
    public void testPutOperationId_whenAlreadyContains_returnFalse_elseTrue(OperationId opId1,
                                                                            OperationId opId2,
                                                                            boolean expected) {
        //when:
        operationIdRepositoryImpl.putOperationId(opId1);
        boolean actual = operationIdRepositoryImpl.putOperationId(opId2);
        //then:
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("sourceForPutCode")
    public void testPutCode_whenAlreadyContains_returnFalse_elseTrue(OperationId opId1,
                                                                     OperationId opId2,
                                                                     Code code,
                                                                     boolean expected) {
        //when:
        operationIdRepositoryImpl.putOperationId(opId1);
        operationIdRepositoryImpl.putCode(opId1, code);
        operationIdRepositoryImpl.putOperationId(opId2);
        boolean actual = operationIdRepositoryImpl.putCode(opId2, code);
        //then:
        Assertions.assertEquals(expected, actual);
    }

    public static Stream<Arguments> sourceForPutOperationIdTest() {
        //given:
        return Stream.of(Arguments.of(new OperationId("123"), new OperationId("123"), false),
                Arguments.of(new OperationId("123"), new OperationId("124"), true));
    }

    public static Stream<Arguments> sourceForPutCode() {
        //given:
        return Stream.of(Arguments.of(new OperationId("123"), new OperationId("123"), new Code("1"), false),
                Arguments.of(new OperationId("123"), new OperationId("333"), new Code("1"), true));
    }
}