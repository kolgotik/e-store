package com.metauniverse.estore.order;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "order")
    private List<Item> items;
    private String userName;
    private String toCountry;
    private String toCity;
    private String toStreet;
    private String postalCode;
    private LocalDateTime shipDate;
}
