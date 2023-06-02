package ru.netology.moneytransfer.repository;

import ru.netology.moneytransfer.model.Card;

public interface CardRepository {

    Card getCard(String number);
}