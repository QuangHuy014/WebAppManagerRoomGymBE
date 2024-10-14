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

    @Override
    public Optional<NguoiDungE> register(NguoiDungDTO userDTO) {
        // Kiểm tra vai trò
        if (nguoiDungRepository.existsByTenNguoiDung(userDTO.getTenNguoiDung())) {
            return Optional.empty();
        }

//        // Mã hóa mật khẩu
//        String encodedPassword = passwordEncoder.encode(userDTO.getMatKhauNguoiDung());

        // Tạo người dùng mới
        NguoiDungE nguoiDungE = new NguoiDungE();
        nguoiDungE.setTenNguoiDung(userDTO.getTenNguoiDung());
//        nguoiDungE.setMatKhauNguoiDung(encodedPassword); // Ghi mật khẩu đã mã hóa
          nguoiDungE.setMatKhauNguoiDung(userDTO.getMatKhauNguoiDung());
        nguoiDungE.setGioiTinhNguoiDung(userDTO.isGioiTinhNguoiDung());
        nguoiDungE.setMoTaNguoiDung(userDTO.getMoTaNguoiDung());
        nguoiDungE.setAnhNguoiDung(userDTO.getAnhNguoiDung());
        nguoiDungE.setVaiTro(userDTO.getVaiTro());
        nguoiDungE.setHoatDongNguoiDung(true);
        nguoiDungE.setDuLieuQrDinhDanh(userDTO.getDuLieuQrDinhDanh());

        // Lưu người dùng mới vào cơ sở dữ liệu
        nguoiDungRepository.save(nguoiDungE);
        return Optional.of(nguoiDungE);
    }

}




