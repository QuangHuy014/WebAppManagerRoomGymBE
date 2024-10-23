package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.service.DangKyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam int maGoiUuDai) {

        DangKyE registration = dangKyService.registerWithDiscount(maThanhVien, maGoiTap, maGoiUuDai);
        return ResponseEntity.ok(registration);
    }

    // Register without discount code
    @PostMapping("/without-discount")
    public ResponseEntity<DangKyE> registerWithoutDiscount(
            @RequestParam int maThanhVien,
            @RequestParam int maGoiTap) {

        DangKyE registration = dangKyService.registerWithoutDiscount(maThanhVien, maGoiTap);
        return ResponseEntity.ok(registration);
    }
}

