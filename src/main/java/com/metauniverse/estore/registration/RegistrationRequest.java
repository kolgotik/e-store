package com.metauniverse.estore.registration;

import com.metauniverse.estore.user.Role;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Set;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {

    private final String username;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String password;
    private final Set<Role> roles;

}
