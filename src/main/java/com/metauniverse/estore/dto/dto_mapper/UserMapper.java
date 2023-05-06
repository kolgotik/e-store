package com.metauniverse.estore.dto.dto_mapper;

import com.metauniverse.estore.dto.user_dto.UserDTO;
import com.metauniverse.estore.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserDTO convertToDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        dto.setBalance(user.getBalance());
        return dto;
    }

    public static User convertFromDTO(UserDTO dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setRoles(dto.getRoles());
        user.setBalance(dto.getBalance());
        return user;
    }
}
