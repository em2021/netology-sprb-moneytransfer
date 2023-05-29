package ru.netology.moneytransfer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferData {
    String cardFromNumber;
    String cardFromValidTill;
    String cardFromCVV;
    String cardToNumber;
    Amount amount;
}