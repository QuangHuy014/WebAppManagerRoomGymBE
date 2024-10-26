package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DoanhThuE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DoanhThuRepo;

import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class HoaDonServiceImpl implements HoaDonService {
 @Autowired
    private HoaDonRepo hoaDonRepo;

    @Autowired
    private DoanhThuRepo doanhThuRepo;

    @Override
    public long countHoaDonByDate(Date date) {
        return hoaDonRepo.findByDate(date).size();
    }

    @Override
    public long countHoaDonByMonth(int month, int year) {
        return hoaDonRepo.findByMonthAndYear(month, year).size();
    }

    @Override
    public long countHoaDonByYear(int year) {
        return hoaDonRepo.findByYear(year).size();
    }

    @Override
    public Double calculateRevenueByDate(Date date) {
        List<HoaDonE> hoaDonList = hoaDonRepo.findByDate(date);
        double totalRevenue = hoaDonList.stream().mapToDouble(HoaDonE::getSoTienThanhToan).sum();

        saveRevenueRecord("Ngày", date, totalRevenue);
        return totalRevenue;
    }

    @Override
    public Double calculateRevenueByMonth(int month, int year) {
        List<HoaDonE> hoaDonList = hoaDonRepo.findByMonthAndYear(month, year);
        double totalRevenue = hoaDonList.stream().mapToDouble(HoaDonE::getSoTienThanhToan).sum();

        saveRevenueRecord("Tháng", new Date(year - 1900, month - 1, 1), totalRevenue);
        return totalRevenue;
    }

    @Override
    public Double calculateRevenueByYear(int year) {
        List<HoaDonE> hoaDonList = hoaDonRepo.findByYear(year);
        double totalRevenue = hoaDonList.stream().mapToDouble(HoaDonE::getSoTienThanhToan).sum();

        saveRevenueRecord("Năm", new Date(year - 1900, 0, 1), totalRevenue);
        return totalRevenue;
    }

    private void saveRevenueRecord(String loaiThoiGian, Date ngay, double soTienDoanhThu) {
        DoanhThuE doanhThu = new DoanhThuE();
        doanhThu.setLoaiThoiGianDoanhThu(loaiThoiGian);
        doanhThu.setNgayTaoDoanhThu(ngay);
        doanhThu.setSoTienDoanhThu(soTienDoanhThu);
        doanhThuRepo.save(doanhThu);
    }

}
