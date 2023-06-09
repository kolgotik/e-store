package com.metauniverse.estore.item;

import lombok.Getter;

@Getter
public enum ItemAvailability {
    ITEM_AVAILABLE("In stock"),
    ITEM_UNAVAILABLE("Out of stock"),
    FEW_ITEMS_LEFT("There are only %s left!");

    private final String value;

    ItemAvailability(String value) {
        this.value = value;
    }
}
