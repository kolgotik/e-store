package com.metauniverse.estore.products;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
@Getter
@Setter
@Component
public abstract class Product {

    private String name;
    private BigDecimal price;

}
