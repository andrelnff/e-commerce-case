package com.teste.ziyou.consumerservice.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Item {
    private String name;
    private String image;
    private Integer qty;
    private BigDecimal cost;
    private String currency;
}