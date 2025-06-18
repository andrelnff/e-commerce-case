package com.teste.ziyou.producerservice.service;

import com.teste.ziyou.producerservice.model.MessageResponse;
import com.teste.ziyou.producerservice.model.Order;

public interface OrderService {
    MessageResponse publishOrder(Order order);
}
