package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhVienRepository;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThanhVienServiceImpl implements ThanhVienService {
  @Autowired
    private ThanhVienRepository thanhVienRepository;

    @Override
    public Optional<ThanhVienE> register(ThanhVienDTO userDTO) {
        if (thanhVienRepository.existsByEmailThanhVien(userDTO.getEmailThanhVien()) ||
            thanhVienRepository.existsByTenThanhVien(userDTO.getTenThanhVien())) {
            return Optional.empty();
        }

        ThanhVienE thanhVien = new ThanhVienE();
        thanhVien.setTenThanhVien(userDTO.getTenThanhVien());
        thanhVien.setEmailThanhVien(userDTO.getEmailThanhVien());
        thanhVien.setMatKhauNguoiDung(userDTO.getMatKhauNguoiDung());
        thanhVien.setSoDienThoaiThanhVien(userDTO.getSoDienThoaiThanhVien());
        thanhVien.setNgaySinhThanhVien(java.sql.Date.valueOf(userDTO.getNgaySinhThanhVien()));

         thanhVien.setDuLieuQrDinhDanh(userDTO.getDuLieuQrDinhDanh());

        // Lưu vào cơ sở dữ liệu
        thanhVienRepository.save(thanhVien);

        return Optional.of(thanhVien);
    }
}
