package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/hoa-don")
public class HoaDonAPI {

    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/count-by-date")
    public long countByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return hoaDonService.countHoaDonByDate(date);
    }

    @GetMapping("/count-by-month")
    public long countByMonth(@RequestParam("month") int month, @RequestParam("year") int year) {
        return hoaDonService.countHoaDonByMonth(month, year);
    }

    @GetMapping("/count-by-year")
    public long countByYear(@RequestParam("year") int year) {
        return hoaDonService.countHoaDonByYear(year);
    }

    @GetMapping("/doanh-thu-by-date")
    public Double getDoanhThuByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return hoaDonService.getDoanhThuByDate(date);
    }

    @GetMapping("/doanh-thu-by-month")
    public Double getDoanhThuByMonth(@RequestParam("month") int month, @RequestParam("year") int year) {
        return hoaDonService.getDoanhThuByMonth(month, year);
    }

    @GetMapping("/doanh-thu-by-year")
    public Double getDoanhThuByYear(@RequestParam("year") int year) {
        return hoaDonService.getDoanhThuByYear(year);
    }

}
