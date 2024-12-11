package org.pizza.crm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.pizza.crm.models.MenuItem;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByAvailability(boolean availability);
    List<MenuItem> findByNameContaining(String name);
}
