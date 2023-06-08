package com.metauniverse.estore.item;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ItemRepository extends CrudRepository<Item, Long> {
    @Query("SELECT i FROM Item i WHERE i.itemType = ?1")
    Iterable<Item> getItemsByType(String itemType);
    @Query("SELECT i FROM Item i WHERE i.id = ?1")
    Iterable<Item> getItemById(Long id);
    @Query("SELECT i.quantity FROM Item i WHERE i.id = ?1")
    Integer getQuantityOfItem(Long id);
}
