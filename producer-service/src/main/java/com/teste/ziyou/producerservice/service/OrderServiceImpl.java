package com.teste.ziyou.producerservice.service;

import com.teste.ziyou.producerservice.model.MessageResponse;
import com.teste.ziyou.producerservice.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}")
    private String queueName;

    @Override
    public MessageResponse publishOrder(Order order) {
        try {
            rabbitTemplate.convertAndSend(queueName, order);
            log.info("Pedido {} enviado para processamento com sucesso", order.getOrder());
            return MessageResponse.builder()
                    .success(true)
                    .message("Pedido " + order.getOrder() + " recebido e enviado para processamento!")
                    .build();

        } catch (Exception e) {
            log.error("Erro ao publicar pedido: {}", e.getMessage());
            return MessageResponse.builder()
                    .success(false)
                    .message("Erro ao processar pedido: " + e.getMessage())
                    .build();
        }
    }
}
