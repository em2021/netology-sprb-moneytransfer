package ru.netology.moneytransfer.service;

import ru.netology.moneytransfer.model.ConfirmationData;
import ru.netology.moneytransfer.model.OperationId;
import ru.netology.moneytransfer.model.TransferData;

public interface MoneyTransferService {

    OperationId makeTransfer(TransferData transferData);

    OperationId confirmOperation(ConfirmationData confirmationData);
}