package ru.netology.moneytransfer.exception;

public class InvalidInput extends RuntimeException {
    public InvalidInput(String msg) {
        super(msg);
    }
}
