package ru.netology.moneytransfer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.moneytransfer.model.ConfirmationData;
import ru.netology.moneytransfer.model.OperationId;
import ru.netology.moneytransfer.model.TransferData;
import ru.netology.moneytransfer.service.MoneyTransferService;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class MoneyTransferController {

    @Autowired
    MoneyTransferService moneyTransferService;

    @PostMapping("/transfer")
    public OperationId transfer(@Valid @RequestBody TransferData transferData) {
        return moneyTransferService.makeTransfer(transferData);
    }

    @PostMapping("/confirmOperation")
    public OperationId confirm(@Valid @RequestBody ConfirmationData confirmationData) {
        return moneyTransferService.confirmOperation(confirmationData);
    }

}
