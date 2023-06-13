package com.metauniverse.estore.item.enums;

import lombok.Getter;

@Getter
public enum ItemAvailability {
    ITEM_AVAILABLE("In stock"),
    ITEM_UNAVAILABLE("Out of stock"),
    FEW_ITEMS_LEFT("There are only %s left!"),
    ITEM_ALREADY_ADDED("This item is already in your cart");

    private final String value;

    ItemAvailability(String value) {
        this.value = value;
    }
}
