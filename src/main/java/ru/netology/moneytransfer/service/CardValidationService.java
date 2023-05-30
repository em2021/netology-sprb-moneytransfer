package ru.netology.moneytransfer.service;

import ru.netology.moneytransfer.model.Card;
import ru.netology.moneytransfer.model.TransferData;

public interface CardValidationService {

    boolean cardIsValid(TransferData transferData, Card card);

    boolean cardsDiffer(TransferData transferData);

    boolean cardExists(Card card);

    boolean expirationDateMatches(TransferData transferData, Card card);

    boolean cvvMatches(TransferData transferData, Card card);
}