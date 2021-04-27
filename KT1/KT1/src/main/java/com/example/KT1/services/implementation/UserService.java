package com.example.KT1.services.implementation;

import com.example.KT1.model.User;
import com.example.KT1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository _userRepository;

    public UserService(UserRepository userRepository) {
        _userRepository = userRepository;
    }

    public User findUserByUsername(String username){
        return _userRepository.findOneByUsername(username);
    }
}
