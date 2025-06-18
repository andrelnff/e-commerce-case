package com.teste.ziyou.consumerservice.controller;

import com.teste.ziyou.consumerservice.model.Order;
import com.teste.ziyou.consumerservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @QueryMapping
    public List<Order> placedOrders() {
        log.info("Executando query GraphQL para obter todos os pedidos");
        return orderService.findAll();
    }
}
