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

    @Override
    public DangKyE registerWithDiscount(int maThanhVien, int maGoiTap, int maGoiUuDai, Date ngayKichHoat, boolean trangThaiDangKy) {
        // Validate member
        ThanhVienE thanhVien = thanhVienRepository.findById(maThanhVien)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Validate discount
        GoiUuDaiE goiUuDai = goiUuDaiRepository.findByMaGoiUuDai(maGoiUuDai)
                .orElseThrow(() -> new RuntimeException("Invalid or expired discount code"));
        DangKyE dangKy = new DangKyE();

        // Validate discount
        Optional<LopHocE> lopHoc = lopHocRepo.findById(dangKy.getLopHoc().getMaLopHoc());

        GoiTapE goiTap = goiUuDai.getGoiTap(); // Truy xuất từ GoiUuDaiE
        // Trong trường hợp không có goiTap, bạn có thể gán là null hoặc thực hiện hành động khác
        if (goiTap == null) {
            goiTap = null; // Hoặc gán tên gói tập mặc định
        }
        dangKy.setGoiUuDai(goiUuDai);
        dangKy.setLopHoc(lopHoc.get());
        dangKy.setThanhVien(thanhVien);
        dangKy.setGoiUuDai(goiUuDai);
        dangKy.setNgayDangKy(new Date());
        dangKy.setNgayKichHoat(ngayKichHoat);
        dangKy.setTrangThaiDangKy(trangThaiDangKy);

        DangKyE savedDangKy = dangKyRepository.save(dangKy);

        ThanhVienDTO thanhVienDTO = new ThanhVienDTO();
        thanhVienDTO.setMaThanhVien(thanhVien.getMaThanhVien());
        thanhVienDTO.setTenThanhVien(thanhVien.getTenThanhVien());
        thanhVienDTO.setEmailThanhVien(thanhVien.getEmailThanhVien());
        thanhVienDTO.setSoDienThoaiThanhVien(thanhVien.getSoDienThoaiThanhVien());
        thanhVienDTO.setNgaySinhThanhVien(thanhVien.getNgaySinhThanhVien());
        thanhVienDTO.setDuLieuQrDinhDanh(thanhVien.getDuLieuQrDinhDanh());
        thanhVienDTO.setMaGoiTap(maGoiTap); // Thêm mã gói tập vào DTO
        thanhVienDTO.setMaDangKy(savedDangKy.getMaDangKy());

        // Chuẩn bị nội dung email
        String subject = "Thông tin đăng ký gói tập";
        String message = String.format("Chào %s,\n\nBạn đã đăng ký thành công gói tập: %s.\n\n" +
                        "Thông tin thành viên:\n" +
                        "- Mã thành viên: %d\n" +
                        "- Tên thành viên: %s\n" +
                        "- Email: %s\n" +
                        "- Số điện thoại: %d\n" +
                        "- Ngày sinh: %s\n" +
                        "- Dữ liệu QR định danh: %s\n\n" +
                        "Cảm ơn bạn đã tham gia!",
                thanhVienDTO.getTenThanhVien(),
                goiTap != null ? goiTap.getTenGoiTap() : "Không có gói tập", // Nếu không có gói tập, hiển thị "Không có gói tập"
                thanhVienDTO.getMaThanhVien(),
                thanhVienDTO.getTenThanhVien(),
                thanhVienDTO.getEmailThanhVien(),
                thanhVienDTO.getSoDienThoaiThanhVien(),
                thanhVienDTO.getNgaySinhThanhVien(),
                thanhVienDTO.getDuLieuQrDinhDanh());

        // Gửi email
        sendMailService.sendEmail(thanhVienDTO, subject, message);

        return savedDangKy;


    }

    @Override
    public DangKyE registerWithoutDiscount(int maThanhVien, int maGoiTap, Date ngayKichHoat, boolean trangThaiDangKy) {
        // Validate member
        ThanhVienE thanhVien = thanhVienRepository.findById(maThanhVien)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Create registration entity
        DangKyE dangKy = new DangKyE();
        dangKy.setThanhVien(thanhVien);
        dangKy.setNgayDangKy(new Date());
        dangKy.setNgayKichHoat(ngayKichHoat);
        dangKy.setTrangThaiDangKy(trangThaiDangKy);

        // Save the registration
        return dangKyRepository.save(dangKy);
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
