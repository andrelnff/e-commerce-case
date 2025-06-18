package com.teste.ziyou.consumerservice.service;

import com.teste.ziyou.consumerservice.model.Order;
import com.teste.ziyou.consumerservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;

    public void saveOrder(Order order) {
        log.info("Pedido recebido da fila: {}", order.getOrder());
        orderRepository.save(order);
        log.info("Pedido salvo: {}", order.getOrder());
    }

    public List<Order> findAll() {
        log.info("Buscando todos os pedidos");
        List<Order> orders = orderRepository.findAll();
        log.info("Pedidos encontrados: {}", orders.size());
        return orders;
    }
}
