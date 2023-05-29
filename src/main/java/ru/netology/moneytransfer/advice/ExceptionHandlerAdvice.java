package ru.netology.moneytransfer.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransfer.exception.InvalidInput;
import ru.netology.moneytransfer.exception.OperationNotConfirmed;
import ru.netology.moneytransfer.exception.TransferNotConfirmed;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(InvalidInput.class)
    public ResponseEntity<Object> icHandler(InvalidInput e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferNotConfirmed.class)
    public ResponseEntity<Object> uuHandler(TransferNotConfirmed e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(OperationNotConfirmed.class)
    public ResponseEntity<Object> uuHandler(OperationNotConfirmed e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}