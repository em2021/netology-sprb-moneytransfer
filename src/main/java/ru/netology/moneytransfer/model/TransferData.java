package ru.netology.moneytransfer.model;

import org.springframework.stereotype.Component;

@Component
public class TransferData {
    String cardFromNumber;
    String cardFromValidTill;
    String cardFromCVV;
    String cardToNumber;
    Amount amount;

    public TransferData() {

    }

    public TransferData(String cardFromNumber,
                        String cardFromValidTill,
                        String cardFromCVV,
                        String cardToNumber,
                        Amount amount) {

        this.cardFromNumber = cardFromNumber;
        this.cardFromValidTill = cardFromValidTill;
        this.cardFromCVV = cardFromCVV;
        this.cardToNumber = cardToNumber;
        this.amount = amount;
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "cardFromNumber='" + cardFromNumber + '\'' +
                ", cardFromValidTill='" + cardFromValidTill + '\'' +
                ", cardFromCVV='" + cardFromCVV + '\'' +
                ", cardToNumber='" + cardToNumber + '\'' +
                ", amount=" + amount +
                '}';
    }
}
