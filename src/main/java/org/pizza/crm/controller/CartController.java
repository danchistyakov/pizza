package org.pizza.crm.controller;

import org.pizza.crm.models.Cart;
import org.pizza.crm.models.MenuItem;
import org.pizza.crm.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController {

    private final MenuService menuService;
    private final Cart cart;

    @Autowired
    public CartController(MenuService menuService, Cart cart) {
        this.menuService = menuService;
        this.cart = cart;
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long id) {
        MenuItem item = menuService.getMenuItemById(id);
        cart.addItem(item);
        return "redirect:/menu";
    }

    @PostMapping("/cart/remove")
    public String removeFromCart(@RequestParam Long id) {
        cart.removeItem(id);
        return "redirect:/menu";
    }

    @PostMapping("/cart/clear")
    public String clearCart() {
        cart.clearCart();
        return "redirect:/menu";
    }
}
