package ru.netology.moneytransfer.model;


import com.fasterxml.jackson.annotation.JsonSetter;
import org.springframework.stereotype.Component;

@Component
public class Amount {

    private Integer value;
    private String currency;

    public Amount() {

    }

    public Amount(Integer value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    @JsonSetter("value")
    public void setValue(Integer value) {
        this.value = value / 100;
    }

    public int getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Amount{" +
                "value=" + value +
                ", currency='" + currency + '\'' +
                '}';
    }
}
