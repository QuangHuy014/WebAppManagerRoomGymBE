package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.repository.DoanhThuRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HoaDonServiceImpl implements HoaDonService {
     @Autowired
    private HoaDonRepo hoaDonRepository;

    @Override
    public long countHoaDonByDate(Date date) {
        return hoaDonRepository.countByDate(date);
    }

    @Override
    public long countHoaDonByMonth(int month, int year) {
        return hoaDonRepository.countByMonthAndYear(month, year);
    }

    @Override
    public long countHoaDonByYear(int year) {
        return hoaDonRepository.countByYear(year);
    }

    @Override
    public Double getDoanhThuByDate(Date date) {
        return hoaDonRepository.sumByDate(date);
    }

    @Override
    public Double getDoanhThuByMonth(int month, int year) {
        return hoaDonRepository.sumByMonthAndYear(month, year);
    }

    @Override
    public Double getDoanhThuByYear(int year) {
        return hoaDonRepository.sumByYear(year);
    }

}
