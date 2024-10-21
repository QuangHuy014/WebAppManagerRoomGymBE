package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.NguoiDungRepository;

import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NguoiDungServiceImpl implements NguoiDungService {
    @Autowired
    private NguoiDungRepository nguoiDungRepository;
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

//        public boolean checkPassword(String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }
    }






