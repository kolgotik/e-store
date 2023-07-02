package com.metauniverse.estore.order;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "order_item",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    @ToString.Exclude
    private List<Item> items;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    @ElementCollection
    private Map<Long, Integer> itemQuantity;
    private String userName;
    private String firstName;
    private String lastName;
    private String toCountry;
    private String toCity;
    private String toStreet;
    private String postalCode;
    private LocalDateTime shipDate;
    private UUID uniqueId;
    private String dateOfOrderPlacement;

}
