package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginRequest;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UserDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSV {
    @Autowired
    private UserRepository userRepository;

    public List<NguoiDungE> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<NguoiDungE> getUserById(int id) {
        return userRepository.findById(id);
    }

    public NguoiDungE insertUser(UserDTO userDTO) {
        NguoiDungE userEntity = new NguoiDungE();
        // Chuyển đổi từ DTO sang Entity
        userEntity.setTenNguoiDung(userDTO.getTenNguoiDung());
        userEntity.setMatKhauNguoiDung(userDTO.getMatKhauNguoiDung());
        userEntity.setGioiTinhNguoiDung(userDTO.isGioiTinhNguoiDung());
        userEntity.setSoDienThoaiNguoiDung(userDTO.getSoDienThoaiNguoiDung());
        userEntity.setMoTaNguoiDung(userDTO.getMoTaNguoiDung());
        userEntity.setAnhNguoiDung(userDTO.getAnhNguoiDung());
        userEntity.setMaVaiTro(userDTO.getMaVaiTro());
        userEntity.setHoatDongNguoiDung(userDTO.isHoatDongNguoiDung());
        userEntity.setDuLieuKhuonMatThanhVien(userDTO.getDuLieuKhuonMatThanhVien());

        return userRepository.save(userEntity);
    }

//    public Optional<NguoiDungE> authenticateUser(LoginRequest loginRequest) {
//        NguoiDungE user = userRepository.findByTenNguoiDung(loginRequest.getUsername());
//        if (user != null && user.getMatKhauNguoiDung().equals(loginRequest.getPassword())) {
//            return Optional.of(user);
//        }
//        return Optional.empty(); // Trả về Optional rỗng nếu không xác thực thành công
//    }
   public Optional<NguoiDungE> authenticateUser(LoginRequest loginRequest) {
    System.out.println("LoginRequest: " + loginRequest.getUsername() + ", " + loginRequest.getPassword());
    NguoiDungE user = userRepository.findByTenNguoiDung(loginRequest.getUsername());
    if (user != null) {
        System.out.println("User found: " + user.getTenNguoiDung());
        if (user.getMatKhauNguoiDung().equals(loginRequest.getPassword())) {
            return Optional.of(user);
        } else {
            System.out.println("Incorrect password");
        }
    } else {
        System.out.println("User not found");
    }
    return Optional.empty();
}


}