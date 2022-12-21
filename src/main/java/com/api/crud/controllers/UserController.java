package com.api.crud.controllers;

import com.api.crud.dtos.UserDto;
import com.api.crud.models.UserModel;
import com.api.crud.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {
    final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> saveUser(@RequestBody @Valid UserDto userDto) {
        if (userService.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: This email is not available");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }

    @GetMapping(value = {"", "/"})
    public ResponseEntity<List<UserModel>> getAll() {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.getAll());
    }


    @GetMapping("/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable(name = "email") String email) {
        Optional<UserModel> userModelOptional = userService.findByEmail(email);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(userModelOptional.get());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Object> deleteUserByEmail(@PathVariable(name = "email") String email) {
        Optional<UserModel> userModelOptional = userService.findByEmail(email);

        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.delete(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }

    @PutMapping("/{email}")
    public ResponseEntity<Object> updateUserByEmail(@PathVariable(name = "email") String email,
                                                    @RequestBody @Valid UserDto userDto) {
        Optional<UserModel> userModelOptional = userService.findByEmail(email);
        if (userModelOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        userModel.setId(userModelOptional.get().getId());
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(userModel));
    }
}
