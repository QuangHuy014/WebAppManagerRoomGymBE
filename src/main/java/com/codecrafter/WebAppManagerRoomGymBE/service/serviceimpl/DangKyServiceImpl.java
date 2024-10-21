package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;

import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.GoiUuDaiRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhVienRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.DangKyService;
import com.codecrafter.WebAppManagerRoomGymBE.utils.ConvertDay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
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

        // Create registration entity
        DangKyE dangKy = new DangKyE();
        dangKy.setThanhVien(thanhVien);
        dangKy.setGoiUuDai(goiUuDai);
        dangKy.setNgayDangKy(new Date());
        dangKy.setTrangThaiDangKy(true);

        // Save the registration
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
}
