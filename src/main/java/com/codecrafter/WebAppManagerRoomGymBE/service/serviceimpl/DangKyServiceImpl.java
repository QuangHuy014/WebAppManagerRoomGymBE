package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;

import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.GoiUuDaiRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhVienRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.DangKyService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DangKyServiceImpl implements DangKyService {

    private final DangKyRepo dangKyRepository;

    private final ThanhVienRepo thanhVienRepository;

    private final GoiUuDaiRepo goiUuDaiRepository;

    @Override
    public DangKyE registerWithDiscount(int maThanhVien, int maGoiTap, int maGoiUuDai) {
        // Validate member
        ThanhVienE thanhVien = thanhVienRepository.findById(maThanhVien)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Validate discount
        GoiUuDaiE goiUuDai = goiUuDaiRepository.findByMaGoiUuDai(maGoiUuDai)
                .orElseThrow(() -> new RuntimeException("Invalid or expired discount code"));

        DangKyE dangKy = new DangKyE();
        dangKy.setThanhVien(thanhVien);
        dangKy.setGoiUuDai(goiUuDai);
        dangKy.setNgayDangKy(new Date());
        dangKy.setTrangThaiDangKy(true);

        return dangKyRepository.save(dangKy);
    }

    @Override
    public DangKyE registerWithoutDiscount(int maThanhVien, int maGoiTap) {
        // Validate member
        ThanhVienE thanhVien = thanhVienRepository.findById(maThanhVien)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Create registration entity
        DangKyE dangKy = new DangKyE();
        dangKy.setThanhVien(thanhVien);
        dangKy.setNgayDangKy(new Date());
        dangKy.setTrangThaiDangKy(true);

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
     @Override
    public List<DangKyE> getAllDangKy() {
        return dangKyRepository.findAll(); // Lấy tất cả các đăng ký
    }
}
