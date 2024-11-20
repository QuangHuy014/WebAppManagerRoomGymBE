package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhVienRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ThanhVienServiceImpl implements ThanhVienService {
    @Autowired
    private ThanhVienRepo thanhVienRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LichSuTapLuyenService lichSuTapLuyenService;
    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private DangKyRepo dangKyRepo;

    @Override
    public Optional<ThanhVienE> register(ThanhVienDTO userDTO) {
        // Kiểm tra email và số điện thoại
        boolean tenThanhVienExists = thanhVienRepository.existsByTenThanhVien(userDTO.getTenThanhVien());
        boolean emailExists = thanhVienRepository.existsByEmailThanhVien(userDTO.getEmailThanhVien());
        boolean soDienThoaiExists = thanhVienRepository.existsBySoDienThoaiThanhVien(userDTO.getSoDienThoaiThanhVien());

        if (tenThanhVienExists) {
            throw new IllegalArgumentException("Tên thành viên đã tồn tại. Vui lòng chọn tên khác.");
        } else if (emailExists && soDienThoaiExists) {
            throw new IllegalArgumentException("Email và số điện thoại đã tồn tại.");
        } else if (emailExists) {
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng chọn email khác.");
        } else if (soDienThoaiExists) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại. Vui lòng chọn số điện thoại khác.");
        }

        // Tạo đối tượng ThanhVienE mới
        ThanhVienE thanhVien = new ThanhVienE();
        thanhVien.setTenThanhVien(userDTO.getTenThanhVien());
        thanhVien.setEmailThanhVien(userDTO.getEmailThanhVien());



        // Mã hóa mật khẩu
//        String encodedPassword = passwordEncoder.encode(userDTO.getMatKhauNguoiDung());
//        thanhVien.setMatKhauNguoiDung(encodedPassword);
         String originalPassword = userDTO.getMatKhauNguoiDung();
        thanhVien.setMatKhauNguoiDung(originalPassword);

        thanhVien.setSoDienThoaiThanhVien(userDTO.getSoDienThoaiThanhVien());
        thanhVien.setNgaySinhThanhVien(userDTO.getNgaySinhThanhVien());

        // Tạo mã QR từ thông tin của thành viên và gán vào thuộc tính DuLieuQrDinhDanh
        String qrCodeData = qrCodeService.GenerateQrCode(userDTO); // Gọi phương thức qua instance
        thanhVien.setDuLieuQrDinhDanh(qrCodeData);

        // Lưu vào cơ sở dữ liệu
        thanhVienRepository.save(thanhVien);
        return Optional.of(thanhVien);

    }


    @Override
    public Optional<ThanhVienE> login(ThanhVienDTO memberDTO) {
        Optional<ThanhVienE> member = thanhVienRepository.findByTenThanhVien(memberDTO.getTenThanhVien());
        if (member.isPresent() && passwordEncoder.matches(memberDTO.getMatKhauNguoiDung(), member.get().getMatKhauNguoiDung())) {
            return member; // Nếu thành viên tồn tại và mật khẩu khớp, trả về đối tượng thành viên
        }
        return Optional.empty(); // Nếu không, trả về Optional.empty()
    }

    public Page<ThanhVienE> getAllMembers(int page, int size, String sortBy, boolean ascending) {
        Sort sort = ascending ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return thanhVienRepository.findAll(pageable);
    }


}
