package com.codecrafter.WebAppManagerRoomGymBE.api;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiUuDaiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/api/goi-uu-dai")
public class GoiUuDaiAPI {
    @Autowired
    private GoiUuDaiService goiUuDaiService;

    @GetMapping("/all")
    public ResponseEntity<List<GoiUuDaiE>> getAllGoiUuDai() {
        List<GoiUuDaiE> goiUuDais = goiUuDaiService.getGoiUuDai();
        return ResponseEntity.ok(goiUuDais);
    }

    @GetMapping("/thanh-vien/{maThanhVien}")
    public ResponseEntity<List<GoiUuDaiE>> getGoiUuDaiByThanhVien(@PathVariable int maThanhVien) {
        List<GoiUuDaiE> goiUuDais = goiUuDaiService.getGoiUuDaiByThanhVien(maThanhVien);
        return ResponseEntity.ok(goiUuDais);
    }

}
