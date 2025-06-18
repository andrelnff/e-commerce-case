package com.teste.ziyou.producerservice.controller;

import com.teste.ziyou.producerservice.model.MessageResponse;
import com.teste.ziyou.producerservice.model.Order;
import com.teste.ziyou.producerservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @MutationMapping
    public MessageResponse publishPlacedOrderMessage(@Argument Order order) {
        log.info("Requisição recebida para publicar pedido: {}", order.getOrder());
        return orderService.publishOrder(order);
    }
}
