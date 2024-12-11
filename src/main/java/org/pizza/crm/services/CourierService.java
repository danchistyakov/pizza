package org.pizza.crm.services;

import org.pizza.crm.models.Courier;
import org.pizza.crm.repositories.CourierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourierService {

    private final CourierRepository courierRepository;

    public CourierService(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }

    public Courier getCourierById(Long id) {
        return courierRepository.findById(id).orElseThrow(() -> new RuntimeException("Курьер не найден"));
    }

    public Courier addCourier(Courier courier) {
        return courierRepository.save(courier);
    }

    public Courier updateCourier(Long id, Courier updatedCourier) {
        Courier courier = getCourierById(id);
        courier.setName(updatedCourier.getName());
        courier.setPhoneNumber(updatedCourier.getPhoneNumber());
        courier.setIsAvailable(updatedCourier.getIsAvailable());
        courier.setImageUrl(updatedCourier.getImageUrl());
        return courierRepository.save(courier);
    }
}
