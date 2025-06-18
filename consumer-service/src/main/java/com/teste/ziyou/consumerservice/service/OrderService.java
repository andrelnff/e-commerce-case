package com.teste.ziyou.consumerservice.service;

import com.teste.ziyou.consumerservice.model.Order;

import java.util.List;

public interface OrderService {
    void saveOrder(Order order);
    List<Order> findAll();
}
