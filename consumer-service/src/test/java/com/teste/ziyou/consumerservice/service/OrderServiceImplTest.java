package com.teste.ziyou.consumerservice.service;

import com.teste.ziyou.consumerservice.model.Order;
import com.teste.ziyou.consumerservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    private OrderRepository orderRepository;
    private OrderServiceImpl orderServiceImpl;
    private Order order;

    @BeforeEach
    void setUp() {
        orderRepository = mock(OrderRepository.class);
        orderServiceImpl = new OrderServiceImpl(orderRepository);

        order = new Order();
        order.setOrder("Test Order");
    }

    @Test
    void shouldSaveOrderSuccessfully_WhenValidOrderProvided() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        assertDoesNotThrow(() -> orderServiceImpl.saveOrder(order));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void shouldRetrieveAllOrders_WhenFindAllMethodIsCalled() {
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> orders = orderServiceImpl.findAll();

        assertEquals(1, orders.size());
        assertEquals("Test Order", orders.get(0).getOrder());

        verify(orderRepository, times(1)).findAll();
    }

}