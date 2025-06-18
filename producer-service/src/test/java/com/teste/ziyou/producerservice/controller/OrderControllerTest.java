package com.teste.ziyou.producerservice.controller;

import com.teste.ziyou.producerservice.model.MessageResponse;
import com.teste.ziyou.producerservice.model.Order;
import com.teste.ziyou.producerservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    private OrderService orderService;
    private OrderController orderController;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);

        testOrder = new Order();
        testOrder.setOrder("Test Order");
    }

    @Test
    void shouldPublishOrderSuccessfully() {
        MessageResponse expectedResponse = MessageResponse.builder()
                        .success(true)
                        .message("Pedido Test Order recebido e enviado para processamento!")
                        .timestamp(Instant.now().toString())
                        .build();

        when(orderService.publishOrder(testOrder)).thenReturn(expectedResponse);

        MessageResponse response = orderController.publishPlacedOrderMessage(testOrder);

        assertNotNull(response);
        assertEquals("Pedido Test Order recebido e enviado para processamento!", response.getMessage());
        verify(orderService, times(1)).publishOrder(testOrder);
    }
}
