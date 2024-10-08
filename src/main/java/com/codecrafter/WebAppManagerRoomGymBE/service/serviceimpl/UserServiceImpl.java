package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UserDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.UserRepository;
import com.codecrafter.WebAppManagerRoomGymBE.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


    @Service
    public class UserServiceImpl implements UserService {
        @Autowired
        private UserRepository userRepository;


         private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

         @Override
    public Optional<NguoiDungE> login(UserDTO userDTO) {
        Optional<NguoiDungE> user = userRepository.findByTenNguoiDung(userDTO.getTenNguoiDung());
        return user;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }





    }
