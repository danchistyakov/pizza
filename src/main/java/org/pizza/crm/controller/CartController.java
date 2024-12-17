package org.pizza.crm.controller;

import org.pizza.crm.models.Cart;
import org.pizza.crm.models.MenuItem;
import org.pizza.crm.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final MenuService menuService;
    private final Cart cart;

    @Autowired
    public CartController(MenuService menuService, Cart cart) {
        this.menuService = menuService;
        this.cart = cart;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<String> addToCart(@PathVariable Long id) {
        MenuItem item = menuService.getMenuItemById(id);
        if (item == null) {
            return new ResponseEntity<>("Menu item not found", HttpStatus.NOT_FOUND);
        }
        cart.addItem(item);
        return new ResponseEntity<>("Item added to cart successfully", HttpStatus.OK);
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long id) {
        cart.removeItem(id);
        return new ResponseEntity<>("Item removed from cart successfully", HttpStatus.OK);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearCart() {
        cart.clearCart();
        return new ResponseEntity<>("Cart cleared successfully", HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<Cart> getCartItems() {
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }
}
