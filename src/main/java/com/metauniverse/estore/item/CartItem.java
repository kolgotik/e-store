package com.metauniverse.estore.item;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CartItem {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private String itemType;
    private String category;
    private Integer quantity;
    private String generalDescription;
    private String detailedDescription;
    private String photo;
    private String video;
    public CartItem() {
    }
    public CartItem(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.brand = item.getBrand();
        this.price = item.getPrice();
        this.itemType = item.getItemType();
        this.category = item.getCategory();
        this.quantity = item.getQuantity();
        this.generalDescription = item.getGeneralDescription();
        this.detailedDescription = item.getDetailedDescription();
        this.photo = item.getPhoto();
        this.video = item.getVideo();
    }
}
