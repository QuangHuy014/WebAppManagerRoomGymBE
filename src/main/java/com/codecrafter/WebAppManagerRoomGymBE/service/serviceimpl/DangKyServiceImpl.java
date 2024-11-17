package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.*;

import com.codecrafter.WebAppManagerRoomGymBE.repository.*;
import com.codecrafter.WebAppManagerRoomGymBE.service.DangKyService;
import com.codecrafter.WebAppManagerRoomGymBE.service.SendMailService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DangKyServiceImpl implements DangKyService {

    private final DangKyRepo dangKyRepository;
    private final ThanhVienRepo thanhVienRepository;
    private final GoiUuDaiRepo goiUuDaiRepository;
    private final LopHocRepo lopHocRepo;
    private final SendMailService sendMailService;
    private final GoiTapRepo goiTapRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DangKyE registerWithDiscount(int maThanhVien, int maGoiTap, Integer maGoiUuDai, Integer maLopHoc, Date ngayKichHoat, boolean trangThaiDangKy) {
        // Validate member
        ThanhVienE thanhVien = thanhVienRepository.findById(maThanhVien)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Validate discount
        GoiUuDaiE goiUuDai = goiUuDaiRepository.findByMaGoiUuDai(maGoiUuDai)
                .orElseThrow(() -> new RuntimeException("Invalid or expired discount code"));

        // Validate and set GoiTap
        GoiTapE goiTap = null;
        if (maGoiTap != 0) {
            goiTap = goiTapRepository.findById(maGoiTap)
                    .orElseThrow(() -> new RuntimeException("Package not found"));
        }

        DangKyE dangKy = new DangKyE();
        dangKy.setGoiUuDai(goiUuDai);
        dangKy.setGoiTap(goiTap); // Gán gói tập vào đăng ký


        if (maLopHoc != null && maLopHoc != 0) {
            LopHocE lopHoc = lopHocRepo.findById(maLopHoc)
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            dangKy.setLopHoc(lopHoc);
        } else {
            dangKy.setLopHoc(null); // Đặt maLopHoc là null nếu không có giá trị
        }

        dangKy.setThanhVien(thanhVien);
        dangKy.setNgayDangKy(new Date());
        dangKy.setNgayKichHoat(ngayKichHoat);
        dangKy.setTrangThaiDangKy(trangThaiDangKy);

        DangKyE savedDangKy = dangKyRepository.save(dangKy);

        // Thiết lập các thuộc tính cho DTO
        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setMaThanhVien(thanhVien.getMaThanhVien());
        thanhVienDTO.setTenThanhVien(thanhVien.getTenThanhVien());
        thanhVienDTO.setEmailThanhVien(thanhVien.getEmailThanhVien());
        thanhVienDTO.setSoDienThoaiThanhVien(thanhVien.getSoDienThoaiThanhVien());
        thanhVienDTO.setNgaySinhThanhVien(thanhVien.getNgaySinhThanhVien());
        thanhVienDTO.setDuLieuQrDinhDanh(thanhVien.getDuLieuQrDinhDanh());
        thanhVienDTO.setMaGoiTap(maGoiTap); // Gán mã gói tập vào DTO
        thanhVienDTO.setMaDangKy(savedDangKy.getMaDangKy());

        // Lấy mật khẩu gốc từ thanhVien
        String originalPassword = thanhVien.getMatKhauNguoiDung();
        String encodedPassword = passwordEncoder.encode(originalPassword);
        thanhVien.setMatKhauNguoiDung(encodedPassword);

        // Chuẩn bị nội dung email
        String subject = "Thông tin đăng ký gói tập";
        String message = String.format("Chào %s,\n\nBạn đã đăng ký thành công gói tập: %s.\nMô tả: %s\nGiá: %.2f\n\n" +
                        "Thông tin thành viên:\n" +
                        "- Mã thành viên: %d\n" +
                        "- Tên thành viên: %s\n" +
                        "- Email: %s\n" +
                        "- Mật khẩu người dùng: %s\n" +
                        "- Số điện thoại: %d\n" +
                        "- Ngày sinh: %s\n" +
                        "- Dữ liệu QR định danh: %s\n\n" +
                        "Cảm ơn bạn đã tham gia!",
                thanhVienDTO.getTenThanhVien(),
                goiTap != null ? goiTap.getTenGoiTap() : "Không có gói tập",
                goiTap != null ? goiTap.getMoTaGoiTap() : "không có mô tả",
                goiTap != null ? goiTap.getGiaGoiTap() : "không có giá gói tập",
                thanhVienDTO.getMaThanhVien(),
                thanhVienDTO.getTenThanhVien(),
                thanhVienDTO.getEmailThanhVien(),
                originalPassword,
                thanhVienDTO.getSoDienThoaiThanhVien(),
                thanhVienDTO.getNgaySinhThanhVien(),
                thanhVienDTO.getDuLieuQrDinhDanh());

        // Gửi email
        sendMailService.sendEmail(thanhVienDTO, subject, message);
        return savedDangKy;
    }

    @Override
    public DangKyE registerWithoutDiscount(int maThanhVien, int maGoiTap, Integer maGoiUuDai, Integer maLopHoc, Date ngayKichHoat, boolean trangThaiDangKy) {
        // Validate member
        ThanhVienE thanhVien = thanhVienRepository.findById(maThanhVien)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        DangKyE dangKy = new DangKyE();

        // Xử lý gói tập
        GoiTapE goiTap = null;
        if (maGoiTap != 0) {
            goiTap = goiTapRepository.findById(maGoiTap)
                    .orElseThrow(() -> new RuntimeException("Invalid package"));
        }
        dangKy.setGoiTap(goiTap);  // Gán gói tập vào đăng ký


        if (maLopHoc != null && maLopHoc != 0) {
            LopHocE lopHoc = lopHocRepo.findById(maLopHoc)
                    .orElseThrow(() -> new RuntimeException("Class not found"));
            dangKy.setLopHoc(lopHoc);
        } else {
            dangKy.setLopHoc(null); // Đặt maLopHoc là null nếu không có giá trị
        }

        // Thiết lập GoiUuDai nếu có
        GoiUuDaiE goiUuDai = null;
        if (maGoiUuDai != null && maGoiUuDai != 0) {  // Check if the discount code is valid
            goiUuDai = goiUuDaiRepository.findByMaGoiUuDai(maGoiUuDai)
                    .orElseThrow(() -> new RuntimeException("Invalid or expired discount code"));
            goiUuDai.setGoiTap(goiTap);
        }

        dangKy.setGoiUuDai(goiUuDai);  // Gán GoUuDai vào DangKy
        dangKy.setThanhVien(thanhVien);
        dangKy.setNgayDangKy(new Date());
        dangKy.setNgayKichHoat(ngayKichHoat);
        dangKy.setTrangThaiDangKy(trangThaiDangKy);

        DangKyE savedDangKy = dangKyRepository.save(dangKy);

        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setMaThanhVien(thanhVien.getMaThanhVien());
        thanhVienDTO.setTenThanhVien(thanhVien.getTenThanhVien());
        thanhVienDTO.setEmailThanhVien(thanhVien.getEmailThanhVien());
        thanhVienDTO.setMatKhauNguoiDung(thanhVien.getMatKhauNguoiDung());
        thanhVienDTO.setSoDienThoaiThanhVien(thanhVien.getSoDienThoaiThanhVien());
        thanhVienDTO.setNgaySinhThanhVien(thanhVien.getNgaySinhThanhVien());
        thanhVienDTO.setDuLieuQrDinhDanh(thanhVien.getDuLieuQrDinhDanh());
        thanhVienDTO.setMaGoiTap(maGoiTap); // Thêm mã gói tập vào DTO
        thanhVienDTO.setMaDangKy(savedDangKy.getMaDangKy());

        String originalPassword = thanhVien.getMatKhauNguoiDung();
        String encodedPassword = passwordEncoder.encode(originalPassword); // Mã hóa mật khẩu
        thanhVien.setMatKhauNguoiDung(encodedPassword);

        // Chuẩn bị nội dung email
        String subject = "Thông tin đăng ký gói tập";
        String message = String.format("Chào %s,\n\nBạn đã đăng ký thành công gói tập: %s.\nMô tả: %s\nGiá: %.2f\n\n" +
                        "Thông tin thành viên:\n" +
                        "- Mã thành viên: %d\n" +
                        "- Tên thành viên: %s\n" +
                        "- Email: %s\n" +
                        "- Mật khẩu người dùng: %s\n" +
                        "- Số điện thoại: %d\n" +
                        "- Ngày sinh: %s\n" +
                        "- Dữ liệu QR định danh: %s\n\n" +
                        "Cảm ơn bạn đã tham gia!",
                thanhVienDTO.getTenThanhVien(),
                goiTap != null ? goiTap.getTenGoiTap() : "Không có gói tập",
                goiTap != null ? goiTap.getMoTaGoiTap() : "không có mô tả",
                goiTap != null ? goiTap.getGiaGoiTap() : "không có giá gói tập",
                thanhVienDTO.getMaThanhVien(),
                thanhVienDTO.getTenThanhVien(),
                thanhVienDTO.getEmailThanhVien(),
                originalPassword,
                thanhVienDTO.getSoDienThoaiThanhVien(),
                thanhVienDTO.getNgaySinhThanhVien(),
                thanhVienDTO.getDuLieuQrDinhDanh());

        sendMailService.sendEmail(thanhVienDTO, subject, message);
        return savedDangKy;
    }


    @Override
    public List<DangKyE> getDangKyByParams(Integer maDangKy, Integer maThanhVien, Integer maGoiUuDai, Date ngayDangKy, Date ngayKichHoat, Boolean trangThaiDangKy, Integer maLopHoc, Integer maHoaDon) {
        return dangKyRepository.findAll((Root<DangKyE> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Điều kiện cho từng trường
            if (maDangKy != null) {
                predicates.add(cb.equal(root.get("maDangKy"), maDangKy));
            }
            if (maThanhVien != null) {
                predicates.add(cb.equal(root.get("thanhVien").get("maThanhVien"), maThanhVien));
            }
            if (maGoiUuDai != null) {
                predicates.add(cb.equal(root.get("goiUuDai").get("maGoiUuDai"), maGoiUuDai));
            }
            if (ngayDangKy != null) {
                predicates.add(cb.equal(root.get("ngayDangKy"), ngayDangKy));
            }
            if (ngayKichHoat != null) {
                predicates.add(cb.equal(root.get("ngayKichHoat"), ngayKichHoat));
            }
            if (trangThaiDangKy != null) {
                predicates.add(cb.equal(root.get("trangThaiDangKy"), trangThaiDangKy));
            }
            if (maLopHoc != null) {
                predicates.add(cb.equal(root.get("lopHoc").get("maLopHoc"), maLopHoc));
            }
            if (maHoaDon != null) {
                predicates.add(cb.equal(root.get("hoaDon").get("maHoaDon"), maHoaDon));
            }

            // Kết hợp các điều kiện
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
}

