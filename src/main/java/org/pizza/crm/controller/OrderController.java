package org.pizza.crm.controller;

import org.pizza.crm.OrderStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.pizza.crm.OrderDto;
import org.pizza.crm.models.Order;
import org.pizza.crm.services.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderDto orderDto, Principal principal) {
        orderService.createOrder(orderDto);

        UserDetails userDetails = (UserDetails) ((Authentication) principal).getPrincipal();

        if (userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/orders";
        } else if (userDetails.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_CLIENT"))) {
            return "redirect:/menu";
        }

        return "redirect:/";
    }

    @GetMapping
    public String ordersPage(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        model.addAttribute("statuses", OrderStatus.values());
        return "orders";
    }


    @PostMapping("/updateStatus")
    public String updateOrderStatusFromTable(@RequestParam Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, OrderStatus.valueOf(status));
        return "redirect:/orders";
    }

}
