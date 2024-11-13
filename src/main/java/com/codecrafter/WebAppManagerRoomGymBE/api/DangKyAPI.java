package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.service.DangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api-public/dang-ky-goi")
public class DangKyAPI {

    @Autowired
    private DangKyService dangKyService;

    // Register with discount code
    @PostMapping("/with-discount")
    public ResponseEntity<DangKyE> registerWithDiscount(
            @RequestParam int maThanhVien,
            @RequestParam int maGoiTap,
            @RequestParam int maGoiUuDai,
            @RequestParam(required = false) Integer maLopHoc,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayKichHoat,
            @RequestParam boolean trangThaiDangKy) {

        DangKyE registration = dangKyService.registerWithDiscount(maThanhVien, maGoiTap, maGoiUuDai, maLopHoc, ngayKichHoat, trangThaiDangKy);
        return ResponseEntity.ok(registration);
    }

    // Register without discount code
    @PostMapping("/without-discount")
    public ResponseEntity<DangKyE> registerWithoutDiscount(
            @RequestParam int maThanhVien,
            @RequestParam int maGoiTap,
            @RequestParam(required = false) Integer maGoiUuDai,
            @RequestParam(required = false) Integer maLopHoc,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayKichHoat,
            @RequestParam boolean trangThaiDangKy) {

        DangKyE registration = dangKyService.registerWithoutDiscount(maThanhVien, maGoiTap, maLopHoc, maGoiUuDai, ngayKichHoat, trangThaiDangKy);
        return ResponseEntity.ok(registration);
    }

    @GetMapping("/search")
    public List<DangKyE> searchDangKy(
            @RequestParam(required = false) Integer maDangKy,
            @RequestParam(required = false) Integer maThanhVien,
            @RequestParam(required = false) Integer maGoiUuDai,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayDangKy,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date ngayKichHoat,
            @RequestParam(required = false) Boolean trangThaiDangKy,
            @RequestParam(required = false) Integer maLopHoc,
            @RequestParam(required = false) Integer maHoaDon
    ) {
        return dangKyService.getDangKyByParams(maDangKy, maThanhVien, maGoiUuDai, ngayDangKy, ngayKichHoat, trangThaiDangKy, maLopHoc, maHoaDon);
    }
}

