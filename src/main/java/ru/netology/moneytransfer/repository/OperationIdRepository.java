package ru.netology.moneytransfer.repository;

import ru.netology.moneytransfer.model.Code;
import ru.netology.moneytransfer.model.OperationId;

public interface OperationIdRepository {

    boolean putOperationId(OperationId operationId);

    boolean putCode(OperationId operationId, Code code);
}