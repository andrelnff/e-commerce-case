package com.teste.ziyou.consumerservice.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document(collection = "placed_orders")
@Getter
@Setter
public class Order {
    @Id
    private String id;
    private String order;
    private String origin;
    private BigDecimal total;
    private String createdAt;
    private List<Item> items;
}

