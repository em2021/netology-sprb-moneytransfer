package ru.netology.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {

    private String number;
    private String expirationMonth;
    private String expirationYear;
    private String cvv;
}