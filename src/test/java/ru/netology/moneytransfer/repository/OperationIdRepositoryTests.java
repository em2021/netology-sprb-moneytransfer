package ru.netology.moneytransfer.repository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.moneytransfer.model.Code;
import ru.netology.moneytransfer.model.OperationId;

import java.util.stream.Stream;

public class OperationIdRepositoryTests {

    private OperationIdRepository operationIdRepository;

    @BeforeAll
    static void beforeAll() {
        System.out.println("OperationIdRepositoryTests tests started");
    }

    @BeforeEach
    void setUp() {
        operationIdRepository = new OperationIdRepository();
        System.out.println("OperationIdRepositoryTests test started");
    }

    @AfterEach
    void tearDown() {
        System.out.println("OperationIdRepositoryTests test completed");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("OperationIdRepositoryTests tests completed");
    }

    @ParameterizedTest
    @MethodSource("sourceForPutOperationIdTest")
    public void testPutOperationId_whenAlreadyContains_returnFalse_elseTrue(OperationId opId1,
                                                                            OperationId opId2,
                                                                            boolean expected) {

        //when:
        operationIdRepository.putOperationId(opId1);
        boolean actual = operationIdRepository.putOperationId(opId2);
        //then:
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("sourceForPutCode")
    public void testPutCode_whenAlreadyContains_returnFalse_elseTrue(OperationId opId1, Code code1,
                                                                     OperationId opId2, Code code2,
                                                                     boolean expected) {
        //when:
        operationIdRepository.putCode(opId1, code1);
        boolean actual = operationIdRepository.putCode(opId2, code2);
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
        return Stream.of(Arguments.of(new OperationId("123"), new Code("1"),
                        new OperationId("123"), new Code("1"), false),
                Arguments.of(new OperationId("123"), new Code("2"),
                        new OperationId("124"), new Code("2"), true));
    }
}
