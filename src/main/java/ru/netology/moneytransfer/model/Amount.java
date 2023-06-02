package ru.netology.moneytransfer.model;


import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {

    private Integer value;
    private String currency;

    @JsonSetter("value")
    public void setValue(Integer value) {
        this.value = value / 100;
    }
}