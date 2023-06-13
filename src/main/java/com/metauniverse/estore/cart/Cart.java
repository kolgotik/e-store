package com.metauniverse.estore.cart;

import com.metauniverse.estore.item.CartItem;
import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "users_cart")
@Getter
@Setter
@NoArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();
    private BigDecimal totalPrice;
    private Integer itemQuantity = 0;
    @Transient
    private Map<Long, Integer> qtyOfEachItem = new HashMap<>();
    @Transient
    private List<CartItem> cartItemList = new ArrayList<>();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "user = " + user + ", " +
                "totalPrice = " + totalPrice + ", " +
                "itemQuantity = " + itemQuantity + ")";
    }
}
