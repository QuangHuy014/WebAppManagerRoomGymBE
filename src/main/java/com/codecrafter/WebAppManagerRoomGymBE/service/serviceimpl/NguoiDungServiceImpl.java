package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.NguoiDungRepository;
import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
    @Service
    public class NguoiDungServiceImpl implements NguoiDungService {
        @Autowired
        private NguoiDungRepository nguoiDungRepository;


         private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

         @Override
    public Optional<NguoiDungE> login(NguoiDungDTO userDTO) {
        Optional<NguoiDungE> user = nguoiDungRepository.findByTenNguoiDung(userDTO.getTenNguoiDung());
        return user;
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }





    }
