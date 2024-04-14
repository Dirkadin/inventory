package com.dirkadin.inventory;

import com.dirkadin.inventory.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Item, Long> {
}
