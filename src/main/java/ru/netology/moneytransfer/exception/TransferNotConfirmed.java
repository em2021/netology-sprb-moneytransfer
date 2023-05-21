package ru.netology.moneytransfer.exception;

public class TransferNotConfirmed extends RuntimeException {
    public TransferNotConfirmed(String msg) {
        super(msg);
    }
}
