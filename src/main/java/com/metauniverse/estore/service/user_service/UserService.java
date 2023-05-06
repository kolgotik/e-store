package com.metauniverse.estore.service.user_service;

import com.metauniverse.estore.repository.user_repo.UserRepository;
import com.metauniverse.estore.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user){
        userRepository.save(user);
    }

}
