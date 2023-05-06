package com.metauniverse.estore.repository.item_repo;

import com.metauniverse.estore.item.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Integer> {
}
