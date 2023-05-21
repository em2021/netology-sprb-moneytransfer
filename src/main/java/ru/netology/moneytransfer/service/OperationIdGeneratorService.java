package ru.netology.moneytransfer.service;

import org.springframework.stereotype.Component;
import ru.netology.moneytransfer.model.IdGenerator;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class OperationIdGeneratorService implements IdGenerator {

    private final AtomicInteger counter;
    private final StringBuilder sb;

    public OperationIdGeneratorService() {
        counter = new AtomicInteger();
        sb = new StringBuilder();
    }

    @Override
    public String generateId() {
        return getPrefix() + padWithZeroes(String.valueOf(counter.getAndIncrement()));
    }

    private String getPrefix() {
        sb.delete(0, sb.length());
        sb.append(LocalDate.now().getYear());
        sb.append(LocalDate.now().getDayOfYear());
        sb.append(LocalDate.now().getDayOfMonth());
        sb.append(LocalDate.now().getDayOfWeek().getValue());
        return sb.toString();
    }

    private String padWithZeroes(String inputString) {
        int length = 10;
        return String.format("%1$" + length + "s", inputString).replace(' ', '0');
    }
}
