package ru.netology.moneytransfer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.netology.moneytransfer.exception.InvalidInput;
import ru.netology.moneytransfer.exception.OperationNotConfirmed;
import ru.netology.moneytransfer.model.*;
import ru.netology.moneytransfer.repository.CardRepository;
import ru.netology.moneytransfer.repository.OperationIdRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final CardRepository cardRepositoryImpl;
    private final OperationIdRepository operationIdRepositoryImpl;
    private final CardValidationService cardValidationService;
    private final Logger logger = LoggerFactory.getLogger("r.n.m.s.MoneyTransferService");

    @Override
    public OperationId makeTransfer(TransferData transferData) {
        Card cardFrom = cardRepositoryImpl.getCard(transferData.getCardFromNumber());
        OperationId operationId = null;
        // Check if card is valid
        if (cardValidationService.cardIsValid(transferData, cardFrom)) {
            operationId = new OperationId(UUID.randomUUID().toString());
            operationIdRepositoryImpl.putOperationId(operationId);
            logger.info("Successful transfer in {}; Operation ID: {}", transferData, operationId);
        }
        return operationId;
    }

    @Override
    public OperationId confirmOperation(ConfirmationData confirmationData) {
        OperationId operationId = confirmationData.getOperationId();
        Code code = confirmationData.getCode();
        if (operationId == null || code == null) {
            logger.error("Error occurred during operation confirmation");
            throw new InvalidInput("Invalid confirmation data");
        }
        if (!operationIdRepositoryImpl.putCode(operationId, code)) {
            logger.error("Error occurred during operation confirmation");
            throw new OperationNotConfirmed("Operation confirmation error");
        }
        logger.info("Operation confirmed");
        return operationId;
    }
}