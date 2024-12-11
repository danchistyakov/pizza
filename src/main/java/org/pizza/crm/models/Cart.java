package org.pizza.crm.models;

import org.pizza.crm.models.MenuItem;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Cart {

    private List<MenuItem> cartItems = new ArrayList<>();
    private Integer totalPrice = 0;

    public List<MenuItem> getCartItems() {
        return cartItems;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void addItem(MenuItem item) {
        cartItems.add(item);
        totalPrice += item.getPrice();
    }

    public void removeItem(Long id) {
        MenuItem item = cartItems.stream()
                .filter(cartItem -> cartItem.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (item != null) {
            cartItems.remove(item);
            totalPrice -= item.getPrice();
        }
    }

    public void clearCart() {
        cartItems.clear();
        totalPrice = 0;
    }
}
