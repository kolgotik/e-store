package com.metauniverse.estore.dto.user_dto;

import com.metauniverse.estore.item.Item;
import com.metauniverse.estore.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private Set<Role> roles;
    private BigDecimal balance;
    private List<Item> items = new ArrayList<>();

}
