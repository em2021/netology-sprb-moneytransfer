package ru.netology.moneytransfer.service;

import org.junit.jupiter.api.*;

public class OperationIdGeneratorServiceTests {

    private OperationIdGeneratorService operationIdGeneratorService;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("OperationIdGeneratorServiceTests tests started");
    }

    @BeforeEach
    public void setUp() {
        operationIdGeneratorService = new OperationIdGeneratorService();
        System.out.println("OperationIdGeneratorServiceTests test started");
    }

    @AfterEach
    public void tearDown() {
        System.out.println("OperationIdGeneratorServiceTests test completed");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("OperationIdGeneratorServiceTests tests completed");
    }

    @Test
    public void testGenerateId_whenCalledMultipleTimes_returnIncrementedId() {
        //given:
        char expected1 = '0';
        char expected2 = '1';
        char expected3 = '2';
        //when:
        String id = operationIdGeneratorService.generateId();
        String id2 = operationIdGeneratorService.generateId();
        String id3 = operationIdGeneratorService.generateId();
        char actual1 = id.charAt(id.length() - 1);
        char actual2 = id2.charAt(id2.length() - 1);
        char actual3 = id3.charAt(id3.length() - 1);
        //then:
        Assertions.assertEquals(expected1, actual1);
        Assertions.assertEquals(expected2, actual2);
        Assertions.assertEquals(expected3, actual3);
    }
}
