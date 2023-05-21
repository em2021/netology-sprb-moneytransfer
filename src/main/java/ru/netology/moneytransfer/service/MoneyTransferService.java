package ru.netology.moneytransfer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.netology.moneytransfer.exception.InvalidInput;
import ru.netology.moneytransfer.exception.OperationNotConfirmed;
import ru.netology.moneytransfer.exception.TransferNotConfirmed;
import ru.netology.moneytransfer.model.Card;
import ru.netology.moneytransfer.model.Code;
import ru.netology.moneytransfer.model.TransferData;
import ru.netology.moneytransfer.repository.CardRepository;

@Service
public class MoneyTransferService {

    @Autowired
    CardRepository cardRepository;
    @Autowired
    OperationIdGeneratorService operationIdGeneratorService;
    private final Logger logger = LoggerFactory.getLogger("r.n.m.s.MoneyTransferService");

    public String makeTransfer(TransferData transferData) {
        // Check if transfer is not being done to the same card
        if (transferData.getCardFromNumber().equals(transferData.getCardToNumber())) {
            logger.error("Error occurred while making transfer to the same card in {}", transferData);
            throw new TransferNotConfirmed("Transfer to the same card is not allowed");
        }
        Card cardFrom = cardRepository.getCard(transferData.getCardFromNumber());
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
        String transactionId = operationIdGeneratorService.generateId();
        logger.info("Successful transfer in {}; Operation ID: {}", transferData, transactionId);
        return transactionId;
    }

    public String confirmOperation(Code code) {
        String verificationCode = code.getCode();
        if (verificationCode == null) {
            logger.error("Error occurred during operation confirmation");
            throw new OperationNotConfirmed("Operation confirmation error");
        }
        logger.info("Operation confirmed");
        return verificationCode;
    }

    private boolean expirationDateMatches(TransferData transaction, Card card) {
        return transaction.getCardFromValidTill().equals(card.getExpirationMonth() + "/" + card.getExpirationYear().substring(2));
    }

    private boolean cvvMatches(TransferData transaction, Card card) {
        return transaction.getCardFromCVV().equals(card.getCvv());
    }
}
