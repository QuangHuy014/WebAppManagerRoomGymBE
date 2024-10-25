package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhVienRepository;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiTapService;
import com.codecrafter.WebAppManagerRoomGymBE.service.SendMailService;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ThanhVienServiceImpl implements ThanhVienService {
    @Autowired
    private ThanhVienRepository thanhVienRepository;

    @Autowired
    private GoiTapService goiTapService;
    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<ThanhVienE> register(ThanhVienDTO userDTO, int maGoiTap) {
        // Kiểm tra xem email hoặc tên thành viên có tồn tại hay không
        if (thanhVienRepository.existsByEmailThanhVien(userDTO.getEmailThanhVien()) ||
                thanhVienRepository.existsByTenThanhVien(userDTO.getTenThanhVien())) {
            return Optional.empty();
        }

        // Tạo một đối tượng ThanhVienE mới
        ThanhVienE thanhVien = new ThanhVienE();
        thanhVien.setTenThanhVien(userDTO.getTenThanhVien());
        thanhVien.setEmailThanhVien(userDTO.getEmailThanhVien());
        thanhVien.setMatKhauNguoiDung(passwordEncoder.encode(userDTO.getMatKhauNguoiDung()));
        thanhVien.setSoDienThoaiThanhVien(userDTO.getSoDienThoaiThanhVien());
        thanhVien.setNgaySinhThanhVien(java.sql.Date.valueOf(userDTO.getNgaySinhThanhVien()));
        thanhVien.setDuLieuQrDinhDanh(userDTO.getDuLieuQrDinhDanh());

        // Lưu vào cơ sở dữ liệu
        thanhVienRepository.save(thanhVien);

        // Lấy thông tin gói tập theo maGoiTap
        Optional<GoiTapE> goiTap = goiTapService.getGoiTapById(maGoiTap);
        if (goiTap.isPresent()) {
            // Chuẩn bị thông tin email
            String subject = "Thông tin đăng ký gói tập";
            String message = String.format("Chào %s,\n\nBạn đã đăng ký thành công gói tập: %s.\nMô tả: %s\nGiá: %.2f\n\n" +
                            "Thông tin thành viên:\n" +
                            "- Tên thành viên: %s\n" +
                            "- Email: %s\n" +
                            "- Số điện thoại: %s\n" +
                            "- Ngày sinh: %s\n" +
                            "- Mật khẩu: %s\n" +
                            "- Dữ liệu QR định danh: %s\n\n" +
                            "Cảm ơn bạn đã tham gia!",
                    thanhVien.getTenThanhVien(),
                    goiTap.get().getTenGoiTap(),
                    goiTap.get().getMoTaGoiTap(),
                    goiTap.get().getGiaGoiTap(),
                    thanhVien.getTenThanhVien(),
                    thanhVien.getEmailThanhVien(),
                    thanhVien.getSoDienThoaiThanhVien(),
                    thanhVien.getNgaySinhThanhVien(),
                    thanhVien.getMatKhauNguoiDung(),
                    thanhVien.getDuLieuQrDinhDanh());

            // Gửi thông tin đến email
            sendMailService.sendEmail(userDTO, subject, message);
        }

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


}
