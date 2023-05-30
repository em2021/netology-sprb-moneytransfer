package ru.netology.moneytransfer.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.netology.moneytransfer.exception.InvalidInput;
import ru.netology.moneytransfer.exception.TransferNotConfirmed;
import ru.netology.moneytransfer.model.Card;
import ru.netology.moneytransfer.model.TransferData;

@Service
@RequiredArgsConstructor
public class CardValidationServiceImpl implements CardValidationService {

    private TransferData transferData;
    private final Logger logger = LoggerFactory.getLogger("r.n.m.s.CardValidationService");

    @Override
    public boolean cardIsValid(TransferData transferData, Card card) {
        this.transferData = transferData;
        return cardsDiffer(transferData)
                && cardExists(card)
                && expirationDateMatches(transferData, card)
                && cvvMatches(transferData, card);
    }

    @Override
    public boolean cardsDiffer(TransferData transferData) {
        // Check if transfer is not being done to the same card
        if (transferData.getCardFromNumber().equals(transferData.getCardToNumber())) {
            logger.error("Error occurred while making transfer to the same card in {}", this.transferData);
            throw new TransferNotConfirmed("Transfer to the same card is not allowed");
        }
        return true;
    }

    @Override
    public boolean cardExists(Card card) {
        if (card == null) {
            logger.error("Error occurred during card validation in {}", this.transferData);
            throw new InvalidInput("Invalid card");
        }
        return true;
    }

    @Override
    public boolean expirationDateMatches(TransferData transferData, Card card) {
        if (!transferData.getCardFromValidTill()
                .equals(card.getExpirationMonth() + "/" + card.getExpirationYear().substring(2))) {
            logger.error("Error occurred during card expiration date validation in {}", this.transferData);
            throw new InvalidInput("Invalid card");
        }
        return true;
    }

    @Override
    public boolean cvvMatches(TransferData transferData, Card card) {
        if (!transferData.getCardFromCVV().equals(card.getCvv())) {
            logger.error("Error occurred during card CVV validation in {}", this.transferData);
            throw new InvalidInput("Invalid card");
        }
        return true;
    }
}