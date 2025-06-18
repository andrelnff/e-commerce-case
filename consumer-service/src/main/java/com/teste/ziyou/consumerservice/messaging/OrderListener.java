package com.teste.ziyou.consumerservice.messaging;

import com.teste.ziyou.consumerservice.model.Order;
import com.teste.ziyou.consumerservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderListener {

    private final OrderService orderService;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveOrder(Order order) {
        log.info("Recebida mensagem da fila: {}", order.getOrder());
        orderService.saveOrder(order);
    }
}
