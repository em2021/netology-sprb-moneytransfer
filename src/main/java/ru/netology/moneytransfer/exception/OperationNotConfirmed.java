package ru.netology.moneytransfer.exception;

public class OperationNotConfirmed extends RuntimeException {
    public OperationNotConfirmed(String msg) {
        super(msg);
    }
}
