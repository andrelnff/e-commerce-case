package com.teste.ziyou.producerservice.service;

import com.teste.ziyou.producerservice.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    private RabbitTemplate rabbitTemplate;
    private OrderService orderService;
    private static final String QUEUE_NAME = "orders.test.queue";

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        orderService = new OrderService(rabbitTemplate);
        ReflectionTestUtils.setField(orderService, "queueName", QUEUE_NAME);
    }

    @Test
    void shouldPublishOrderSuccessfully() {
        Order order = new Order();
        order.setOrder("Test Order");

        doNothing().when(rabbitTemplate).convertAndSend(QUEUE_NAME, order);

        String response = orderService.publishOrder(order);

        assertNotNull(response);
        assertTrue(response.contains("Pedido Test Order recebido e enviado para processamento!"));
        verify(rabbitTemplate, times(1)).convertAndSend(QUEUE_NAME, order);
    }

    @Test
    void shouldThrowException_WhenPublishOrderFails() {
        Order order = new Order();
        order.setOrder("Test Order");

        doThrow(new RuntimeException("RabbitMQ error"))
            .when(rabbitTemplate).convertAndSend(QUEUE_NAME, order);

        assertThrows(RuntimeException.class, () -> orderService.publishOrder(order));

        verify(rabbitTemplate, times(1)).convertAndSend(QUEUE_NAME, order);
    }
}