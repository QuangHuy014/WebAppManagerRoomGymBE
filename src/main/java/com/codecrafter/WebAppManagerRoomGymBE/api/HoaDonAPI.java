package com.codecrafter.WebAppManagerRoomGymBE.api;


import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/hoa-don")
public class HoaDonAPI {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/hoa-don-by-month")
    public ResponseEntity<Map<String, Object>> getHoaDonByMonth(
            @RequestParam("month") int month,
            @RequestParam("year") int year) {
        Map<String, Object> result = hoaDonService.getHoaDonDetailsByMonth(month, year);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/hoa-don-by-year")
    public ResponseEntity<Map<String, Object>> getHoaDonByYear(@RequestParam("year") int year) {
        Map<String, Object> result = hoaDonService.getHoaDonDetailsByYear(year);
        return ResponseEntity.ok(result);
    }



}
