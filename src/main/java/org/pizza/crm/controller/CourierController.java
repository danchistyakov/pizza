package org.pizza.crm.controller;

import org.pizza.crm.models.Courier;
import org.pizza.crm.services.CourierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/couriers")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @GetMapping
    public String listCouriers(Model model) {
        List<Courier> couriers = courierService.getAllCouriers();
        model.addAttribute("couriers", couriers);
        return "couriers";
    }

    @GetMapping("/create")
    public String createCourierPage(Model model) {
        model.addAttribute("courier", new Courier());
        return "courier-form";
    }

    @PostMapping("/create")
    public String createCourier(@ModelAttribute Courier courier, @RequestParam(required = false) Boolean isAvailable) {
        courier.setIsAvailable(isAvailable != null && isAvailable);
        courierService.addCourier(courier);
        return "redirect:/couriers";
    }

    @GetMapping("/edit/{id}")
    public String editCourierPage(@PathVariable Long id, Model model) {
        Courier courier = courierService.getCourierById(id);
        model.addAttribute("courier", courier);
        return "courier-form";
    }

    @PostMapping("/edit/{id}")
    public String updateCourier(@PathVariable Long id, @ModelAttribute Courier courier, @RequestParam(required = false) Boolean isAvailable) {
        courier.setIsAvailable(isAvailable != null && isAvailable);
        courierService.updateCourier(id, courier);
        return "redirect:/couriers";
    }
}
