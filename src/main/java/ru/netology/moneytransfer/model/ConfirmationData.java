package ru.netology.moneytransfer.model;

import lombok.Data;

@Data
public class ConfirmationData {

    private final Code code;
    private final OperationId operationId;
}