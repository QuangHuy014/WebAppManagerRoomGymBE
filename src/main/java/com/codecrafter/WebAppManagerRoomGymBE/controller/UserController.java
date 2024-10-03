package com.codecrafter.WebAppManagerRoomGymBE.controller;


import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginRequest;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UserDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.service.UserSV;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserSV userSV;

    @GetMapping("/users")
public List<NguoiDungE> getAllUsers() {
    return userSV.getAllUsers();
}

@PostMapping("/users")
public ResponseEntity<NguoiDungE> createUser(@RequestBody UserDTO userDTO) {
    NguoiDungE newUser = userSV.insertUser(userDTO);
    return ResponseEntity.ok(newUser);
}

@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    System.out.println("Login attempt: " + loginRequest.getUsername());
    Optional<NguoiDungE> user = userSV.authenticateUser(loginRequest);
    if (user.isPresent()) {
        return ResponseEntity.ok("Login successful");
    } else {
        return ResponseEntity.status(401).body("Invalid username or password");
    }
}



}
