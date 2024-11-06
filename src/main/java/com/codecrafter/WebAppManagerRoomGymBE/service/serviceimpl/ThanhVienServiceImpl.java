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
    private GoiTapService goiTapService;
    @Autowired
    private SendMailService sendMailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LichSuTapLuyenService lichSuTapLuyenService;
    @Autowired
    private QrCodeService qrCodeService;
    @Autowired
    private DangKyRepo dangKyRepo;

    @Override
    public Optional<ThanhVienE> register(ThanhVienDTO userDTO, int maGoiTap) {
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
        String encodedPassword = passwordEncoder.encode(userDTO.getMatKhauNguoiDung());
        thanhVien.setMatKhauNguoiDung(encodedPassword);

        thanhVien.setSoDienThoaiThanhVien(userDTO.getSoDienThoaiThanhVien());
        thanhVien.setNgaySinhThanhVien(userDTO.getNgaySinhThanhVien());

        // Tạo mã QR từ thông tin của thành viên và gán vào thuộc tính DuLieuQrDinhDanh
        String qrCodeData = qrCodeService.GenerateQrCode(userDTO); // Gọi phương thức qua instance
        thanhVien.setDuLieuQrDinhDanh(qrCodeData);

        // Lưu vào cơ sở dữ liệu
        thanhVienRepository.save(thanhVien);

        // Lấy thông tin gói tập và gửi email
//        Optional<GoiTapE> goiTap = goiTapService.getGoiTapById(maGoiTap);
//        if (goiTap.isPresent()) {
//            String subject = "Thông tin đăng ký gói tập";
//            String message = String.format("Chào %s,\n\nBạn đã đăng ký thành công gói tập: %s.\nMô tả: %s\nGiá: %.2f\n\n" +
//                            "Thông tin thành viên:\n" +
//                            "- Tên thành viên: %s\n" +
//                            "- Email: %s\n" +
//                            "- Số điện thoại: %s\n" +
//                            "- Ngày sinh: %s\n" +
//                            "- Mật khẩu: %s\n" +
//                            "- Dữ liệu QR định danh: %s\n\n" +
//                            "Cảm ơn bạn đã tham gia!",
//                    thanhVien.getTenThanhVien(),
//                    goiTap.get().getTenGoiTap(),
//                    goiTap.get().getMoTaGoiTap(),
//                    goiTap.get().getGiaGoiTap(),
//                    thanhVien.getTenThanhVien(),
//                    thanhVien.getEmailThanhVien(),
//                    thanhVien.getSoDienThoaiThanhVien(),
//                    thanhVien.getNgaySinhThanhVien(),
//                    userDTO.getMatKhauNguoiDung(),
//                    thanhVien.getDuLieuQrDinhDanh());
//
//            sendMailService.sendEmail(userDTO, subject, message);
//        }
//
//        return Optional.of(thanhVien);
       GoiTapE goiTap = goiTapService.getGoiTapById(maGoiTap)
                                   .orElseThrow(() -> new IllegalArgumentException("Gói tập không tồn tại"));
    DangKyE dangKy = new DangKyE();
    dangKy.setThanhVien(thanhVien);
    dangKy.setGoiTap(goiTap);
    dangKy.setNgayDangKy(new Date());
    dangKy.setTrangThaiDangKy(true);
    dangKyRepo.save(dangKy);

    // Định dạng nội dung email
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
            thanhVien.getTenThanhVien(),               // Tên thành viên
            goiTap.getTenGoiTap(),                     // Tên gói tập
            goiTap.getMoTaGoiTap(),                    // Mô tả gói tập
            goiTap.getGiaGoiTap(),                     // Giá gói tập
            thanhVien.getTenThanhVien(),               // Tên thành viên
            thanhVien.getEmailThanhVien(),             // Email thành viên
            thanhVien.getSoDienThoaiThanhVien(),       // Số điện thoại thành viên
            thanhVien.getNgaySinhThanhVien(),          // Ngày sinh thành viên
            userDTO.getMatKhauNguoiDung(),             // Mật khẩu thành viên
            thanhVien.getDuLieuQrDinhDanh());          // Dữ liệu QR định danh

    // Gửi email xác nhận đăng ký gói tập
    sendMailService.sendEmail(userDTO, subject, message);

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
