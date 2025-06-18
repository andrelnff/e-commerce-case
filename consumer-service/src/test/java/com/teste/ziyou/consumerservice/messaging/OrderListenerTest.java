package com.teste.ziyou.consumerservice.messaging;

import com.teste.ziyou.consumerservice.model.Order;
import com.teste.ziyou.consumerservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderListenerTest {

    private OrderService orderService;
    private OrderListener orderListener;

    @BeforeEach
    void setUp() {
        orderService = mock(OrderService.class);
        orderListener = new OrderListener(orderService);
    }

    @Test
    void shouldProcessMessageSuccessfully_WhenOrderMessageIsReceived() {
        Order order = new Order();
        order.setOrder("Test Order");

        doNothing().when(orderService).saveOrder(any(Order.class));

        assertDoesNotThrow(() -> orderListener.receiveOrder(order));
        verify(orderService, times(1)).saveOrder(any(Order.class));
    }

}