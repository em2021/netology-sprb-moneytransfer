package ru.netology.moneytransfer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.netology.moneytransfer.exception.InvalidInput;
import ru.netology.moneytransfer.exception.OperationNotConfirmed;
import ru.netology.moneytransfer.exception.TransferNotConfirmed;
import ru.netology.moneytransfer.model.*;
import ru.netology.moneytransfer.repository.CardRepository;
import ru.netology.moneytransfer.repository.OperationIdRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MoneyTransferServiceImpl implements MoneyTransferService {

    private final CardRepository cardRepositoryImpl;
    private final OperationIdRepository operationIdRepositoryImpl;
    private final Logger logger = LoggerFactory.getLogger("r.n.m.s.MoneyTransferService");

    @Override
    public OperationId makeTransfer(TransferData transferData) {
        // Check if transfer is not being done to the same card
        if (transferData.getCardFromNumber().equals(transferData.getCardToNumber())) {
            logger.error("Error occurred while making transfer to the same card in {}", transferData);
            throw new TransferNotConfirmed("Transfer to the same card is not allowed");
        }
        Card cardFrom = cardRepositoryImpl.getCard(transferData.getCardFromNumber());
        // Check if card is valid
        if (cardFrom == null) {
            logger.error("Error occurred during card validation in {}", transferData);
            throw new InvalidInput("Invalid card");
        }
        if (!expirationDateMatches(transferData, cardFrom)) {
            logger.error("Error occurred during card expiration date validation in {}", transferData);
            throw new InvalidInput("Invalid card");
        }
        if (!cvvMatches(transferData, cardFrom)) {
            logger.error("Error occurred during card CVV validation in {}", transferData);
            throw new InvalidInput("Invalid card");
        }
        OperationId operationId = new OperationId(UUID.randomUUID().toString());
        operationIdRepositoryImpl.putOperationId(operationId);
        logger.info("Successful transfer in {}; Operation ID: {}", transferData, operationId);
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

    private boolean expirationDateMatches(TransferData transaction, Card card) {
        return transaction.getCardFromValidTill().equals(card.getExpirationMonth() + "/" + card.getExpirationYear().substring(2));
    }

    private boolean cvvMatches(TransferData transaction, Card card) {
        return transaction.getCardFromCVV().equals(card.getCvv());
    }
}