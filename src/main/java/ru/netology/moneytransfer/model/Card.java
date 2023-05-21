package ru.netology.moneytransfer.model;

import org.springframework.stereotype.Component;

@Component
public class Card {

    private String number;
    private String expirationMonth;
    private String expirationYear;
    private String cvv;

    public Card() {

    }

    public Card(String number, String expirationMonth, String expirationYear, String cvv) {
        this.number = number;
        this.expirationYear = expirationYear;
        this.expirationMonth = expirationMonth;
        this.cvv = cvv;
    }

    public String getNumber() {
        return number;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public String getCvv() {
        return cvv;
    }
}
