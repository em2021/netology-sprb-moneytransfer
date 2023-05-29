package ru.netology.moneytransfer.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransfer.model.Card;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CardRepository {

    private Map<String, Card> validCards = new ConcurrentHashMap<>();

    public CardRepository() {
        init();
    }

    public Card getCard(String number) {
        return validCards.get(number);
    }

    private void init() {
        Card card1 = new Card("1111111111111111", "12", "2023", "123");
        Card card2 = new Card("2222222222222222", "12", "2023", "321");
        validCards.put(card1.getNumber(), card1);
        validCards.put(card2.getNumber(), card2);
    }
}