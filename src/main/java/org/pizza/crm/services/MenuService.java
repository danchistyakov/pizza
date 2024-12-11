package org.pizza.crm.services;

import org.pizza.crm.models.MenuItem;
import org.pizza.crm.repositories.MenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    public MenuItem getMenuItemById(Long id) {
        return menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Позиция не найдена"));
    }

    public MenuItem addMenuItem(MenuItem menuItem) {
        return menuRepository.save(menuItem);
    }

    public MenuItem updateMenuItem(Long id, MenuItem updatedItem) {
        MenuItem menuItem = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Позиция не найдена"));
        menuItem.setName(updatedItem.getName());
        menuItem.setDescription(updatedItem.getDescription());
        menuItem.setImageUrl(updatedItem.getImageUrl());
        menuItem.setPrice(updatedItem.getPrice());
        menuItem.setAvailability(updatedItem.getAvailability());
        return menuRepository.save(menuItem);
    }

    public List<MenuItem> getAvailableMenuItems() {
        return menuRepository.findByAvailability(true);
    }

    public List<MenuItem> searchMenuItems(String name) {
        return menuRepository.findByNameContaining(name);
    }

    public void deleteMenuItem(Long id) {
        menuRepository.deleteById(id);
    }
}
