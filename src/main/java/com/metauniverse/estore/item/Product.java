package com.metauniverse.estore.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Getter
@Setter
@Component
public abstract class Product {

    private String name;
    private BigDecimal price;

}
