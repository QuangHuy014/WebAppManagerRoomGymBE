package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.CustomUserDetailService;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.NguoiDungRepo;

import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NguoiDungServiceImpl implements NguoiDungService {
    @Autowired
    private NguoiDungRepo nguoiDungRepository;

    @Override
    public Optional<NguoiDungE> login(NguoiDungDTO userDTO) {
        Optional<NguoiDungE> user = nguoiDungRepository.findByTenNguoiDung(userDTO.getTenNguoiDung());
        return user;
    }


    @Override
    public Optional<NguoiDungE> findByUserName(String userName) {
        Optional<NguoiDungE> user = nguoiDungRepository.findByTenNguoiDung(userName);
        return user;
    }

    @Override
    public Optional<NguoiDungE> getUserInfo(int id) {
        return nguoiDungRepository.findById(id);
    }


//        public boolean checkPassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }


}






