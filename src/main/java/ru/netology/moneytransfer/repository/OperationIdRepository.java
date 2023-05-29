package ru.netology.moneytransfer.repository;


import org.springframework.stereotype.Repository;
import ru.netology.moneytransfer.model.Code;
import ru.netology.moneytransfer.model.OperationId;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class OperationIdRepository {

    private final Map<OperationId, Code> validOperationIds = new ConcurrentHashMap<>();

    public boolean putOperationId(OperationId operationId) {
        if (validOperationIds.containsKey(operationId)) {
            return false;
        }
        validOperationIds.putIfAbsent(operationId, new Code());
        return true;
    }

    public boolean putCode(OperationId operationId, Code code) {
        if (validOperationIds.get(operationId) == null) {
            validOperationIds.put(operationId, code);
            return true;
        }
        return false;
    }
}