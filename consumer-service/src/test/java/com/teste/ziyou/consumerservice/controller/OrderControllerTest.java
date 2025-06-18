package com.teste.ziyou.consumerservice.controller;

import com.teste.ziyou.consumerservice.model.Order;
import com.teste.ziyou.consumerservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class OrderControllerTest {

    private OrderService orderService;
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);
    }

    @Test
    void shouldReturnAllOrders_WhenPlacedOrdersEndpointIsCalled() {
        Order order = new Order();
        order.setOrder("Test Order");

        when(orderService.findAll()).thenReturn(List.of(order));

        List<Order> orders = orderController.placedOrders();

        assertEquals(1, orders.size());
        assertEquals("Test Order", orders.get(0).getOrder());

        verify(orderService, times(1)).findAll();
    }

}