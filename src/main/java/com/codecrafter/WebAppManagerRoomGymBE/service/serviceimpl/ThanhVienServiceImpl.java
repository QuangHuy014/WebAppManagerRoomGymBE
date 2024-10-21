package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.ThanhVienM;
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
    public Optional<ThanhVienM> register(ThanhVienDTO userDTO) {
        // Kiểm tra xem email hoặc tên thành viên đã tồn tại hay chưa
        if (thanhVienRepository.existsByEmailThanhVien(userDTO.getEmailThanhVien()) ||
                thanhVienRepository.existsByTenThanhVien(userDTO.getTenThanhVien())) {
            return Optional.empty(); // Nếu tồn tại thì trả về Optional rỗng
        }

        // Tạo đối tượng ThanhVienE từ thông tin trong userDTO
        ThanhVienE thanhVienE = new ThanhVienE();
        thanhVienE.setTenThanhVien(userDTO.getTenThanhVien());
        thanhVienE.setEmailThanhVien(userDTO.getEmailThanhVien());
        thanhVienE.setMatKhauNguoiDung(userDTO.getMatKhauNguoiDung());
        thanhVienE.setSoDienThoaiThanhVien(userDTO.getSoDienThoaiThanhVien());
        thanhVienE.setNgaySinhThanhVien(java.sql.Date.valueOf(userDTO.getNgaySinhThanhVien()));
        thanhVienE.setDuLieuQrDinhDanh(userDTO.getDuLieuQrDinhDanh());

        // Lưu đối tượng ThanhVienE vào cơ sở dữ liệu
        thanhVienRepository.save(thanhVienE);

        // Chuyển đổi ThanhVienE thành ThanhVienM và trả về
        ThanhVienM thanhVienM = ThanhVienM.convertMemberEToMemberM(thanhVienE);
        return Optional.of(thanhVienM);
    }

}
