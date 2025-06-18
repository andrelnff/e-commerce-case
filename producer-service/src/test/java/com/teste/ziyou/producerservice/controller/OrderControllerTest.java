package com.teste.ziyou.producerservice.controller;

import com.teste.ziyou.producerservice.model.Order;
import com.teste.ziyou.producerservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
        when(orderService.publishOrder(testOrder)).thenReturn("Pedido Test Order recebido e enviado para processamento!");

        String response = orderController.publishPlacedOrderMessage(testOrder);

        assertNotNull(response);
        assertEquals("Pedido Test Order recebido e enviado para processamento!", response);
        verify(orderService, times(1)).publishOrder(testOrder);
    }
}
