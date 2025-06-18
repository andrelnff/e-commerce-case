package com.teste.ziyou.producerservice.service;

import com.teste.ziyou.producerservice.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    public String publishOrder(Order order) {
        log.info("Publicando pedido: {}", order);
        rabbitTemplate.convertAndSend(queueName, order);
        log.info("Pedido {} enviado para processamento", order.getOrder());
        return "Pedido " + order.getOrder() + " recebido e enviado para processamento!";
    }
}
