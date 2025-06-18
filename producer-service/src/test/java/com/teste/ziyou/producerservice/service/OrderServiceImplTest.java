package com.teste.ziyou.producerservice.service;

import com.teste.ziyou.producerservice.model.MessageResponse;
import com.teste.ziyou.producerservice.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private RabbitTemplate rabbitTemplate;
    private OrderServiceImpl orderServiceImpl;
    private static final String QUEUE_NAME = "orders.test.queue";

    @BeforeEach
    void setUp() {
        rabbitTemplate = mock(RabbitTemplate.class);
        orderServiceImpl = new OrderServiceImpl(rabbitTemplate);
        ReflectionTestUtils.setField(orderServiceImpl, "queueName", QUEUE_NAME);
    }    @Test
    void shouldPublishOrderSuccessfully() {
        Order order = new Order();
        order.setOrder("Test Order");

        doNothing().when(rabbitTemplate).convertAndSend(QUEUE_NAME, order);

        MessageResponse response = orderServiceImpl.publishOrder(order);

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getTimestamp());
        assertEquals("Pedido Test Order recebido e enviado para processamento!", response.getMessage());
        verify(rabbitTemplate, times(1)).convertAndSend(QUEUE_NAME, order);
    }

    @Test
    void shouldReturnFailureResponse_WhenPublishOrderFails() {
        Order order = new Order();
        order.setOrder("Test Order");

        doThrow(new RuntimeException("RabbitMQ error"))
            .when(rabbitTemplate).convertAndSend(QUEUE_NAME, order);

        MessageResponse response = orderServiceImpl.publishOrder(order);

        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertNotNull(response.getTimestamp());
        assertEquals("Erro ao processar pedido: RabbitMQ error", response.getMessage());
        verify(rabbitTemplate, times(1)).convertAndSend(QUEUE_NAME, order);
    }
}