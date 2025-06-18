package com.teste.ziyou.producerservice.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class Order {
    private String order;
    private String origin;
    private BigDecimal total;
    private String createdAt;
    private List<Item> items;
}

