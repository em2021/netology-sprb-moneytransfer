package ru.netology.moneytransfer.repository;

import org.springframework.stereotype.Repository;
import ru.netology.moneytransfer.model.Card;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class CardRepository {

    private ConcurrentMap<String, Card> validCards = init();

    public Card getCard(String number) {
        return validCards.get(number);
    }

    private static ConcurrentMap<String, Card> init() {
        ConcurrentMap<String, Card> cards = new ConcurrentHashMap<>();
        Card card1 = new Card("1111111111111111", "12", "2023", "123");
        Card card2 = new Card("2222222222222222", "12", "2023", "321");
        cards.put(card1.getNumber(), card1);
        cards.put(card2.getNumber(), card2);
        return cards;
    }
}
