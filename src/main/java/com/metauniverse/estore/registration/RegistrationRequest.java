package com.metauniverse.estore.registration;

import com.metauniverse.estore.user.Role;
import lombok.*;

import java.util.Set;

@Setter
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
    private final Boolean locked;
    private final Boolean enabled;
}
