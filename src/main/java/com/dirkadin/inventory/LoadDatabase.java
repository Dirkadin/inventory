package com.dirkadin.inventory;

import com.dirkadin.inventory.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {
  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(InventoryRepository itemRepository) {
    return args -> {
      log.info("Preloading" + itemRepository.save(new Item("Bananas", 1)));
      log.info("Preloading" + itemRepository.save(new Item("Peaches", 2)));
    };
  }
}
