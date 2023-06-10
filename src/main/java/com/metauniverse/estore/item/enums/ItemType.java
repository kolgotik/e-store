package com.metauniverse.estore.item.enums;

import lombok.Getter;

@Getter
public enum ItemType {
    ANDROID_SMARTPHONE("android smartphone"),
    APPLE_SMARTPHONE("apple smartphone"),
    DESKTOP_PC("desktop pc"),
    LAPTOP("laptop"),
    PC_COMPONENT("pc component"),
    CONSOLE("console");
    private final String value;

    ItemType(String value) {
        this.value = value;
    }
}
