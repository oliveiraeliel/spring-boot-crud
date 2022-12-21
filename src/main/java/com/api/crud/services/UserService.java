package com.api.crud.services;

import com.api.crud.dtos.UserDto;
import com.api.crud.models.UserModel;
import com.api.crud.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel save(UserModel userModel) {
        return userRepository.save(userModel);
    }

    public List<UserModel> getAll() {
        return userRepository.findAll();
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public Optional<UserModel> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }


}
