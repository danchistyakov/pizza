package org.pizza.crm.controller;

import org.pizza.crm.models.Cart;
import org.pizza.crm.models.MenuItem;
import org.pizza.crm.services.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller

public class MenuController {
    private final Cart cart;
    private final MenuService menuService;

    public MenuController(Cart cart, MenuService menuService) {
        this.cart = cart;
        this.menuService = menuService;
    }

    @GetMapping("/menu")
    public String menuPage(Model model) {
        model.addAttribute("menuItems", menuService.getAllMenuItems());
        model.addAttribute("cartItems", cart.getCartItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        model.addAttribute("pageTitle", "Меню | BroPizza");
        return "menu";
    }

    @GetMapping("/menu/search")
    public String searchMenuItems(@RequestParam String name, Model model) {
        List<MenuItem> items = menuService.searchMenuItems(name);
        model.addAttribute("menuItems", items);
        model.addAttribute("cartItems", cart.getCartItems());
        model.addAttribute("totalPrice", cart.getTotalPrice());
        model.addAttribute("pageTitle", "Меню | BroPizza");
        return "menu";
    }

    @GetMapping("/menu/create")
    public String createMenuItemPage(Model model) {
        model.addAttribute("menuItem", new MenuItem());
        return "menu-form";
    }

    @PostMapping("/menu/create")
    public String createMenuItem(@ModelAttribute MenuItem menuItem) {
        menuService.addMenuItem(menuItem);
        return "redirect:/menu";
    }

    @GetMapping("/menu/delete/{id}")
    public String deleteMenuItem(@PathVariable Long id) {
        menuService.deleteMenuItem(id);
        return "redirect:/menu";
    }

    @GetMapping("/menu/edit/{id}")
    public String editMenuItemPage(@PathVariable Long id, Model model) {
        MenuItem menuItem = menuService.getMenuItemById(id);
        model.addAttribute("menuItem", menuItem);
        return "menu-form";
    }

    @PostMapping("/menu/edit/{id}")
    public String editMenuItem(@PathVariable Long id, @ModelAttribute MenuItem menuItem, @RequestParam(required = false) Boolean availability) {
        menuItem.setAvailability(availability != null && availability);
        menuService.updateMenuItem(id, menuItem);
        return "redirect:/menu";
    }
}
