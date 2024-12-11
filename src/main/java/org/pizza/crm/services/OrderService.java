package org.pizza.crm.services;

import org.pizza.crm.OrderDto;
import org.pizza.crm.OrderStatus;
import org.pizza.crm.models.MenuItem;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.pizza.crm.repositories.OrderRepository;
import org.pizza.crm.models.Cart;
import org.pizza.crm.models.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final Cart cart;

    @Autowired
    public OrderService(OrderRepository orderRepository, Cart cart) {
        this.orderRepository = orderRepository;
        this.cart = cart;
    }

    public void createOrder(OrderDto orderDto) {
        List<MenuItem> menuItems = new ArrayList<>(cart.getCartItems());
        if (menuItems.isEmpty()) {
            throw new RuntimeException("Корзина пуста! Невозможно создать заказ.");
        }
        Order order = new Order();
        order.setMenuItems(menuItems);
        order.setOrderDate(new Date());
        order.setTotalAmount(orderDto.getTotalAmount());
        order.setStatus(OrderStatus.СОЗДАН);
        orderRepository.save(order);
        cart.clearCart();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Заказ не найден"));
    }

    public void updateOrderStatus(Long id, OrderStatus status) {
        Order order = getOrderById(id);
        order.setStatus(status);
        orderRepository.save(order);
    }
}
