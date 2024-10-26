package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.HoaDonDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DoanhThuE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DoanhThuRepo;

import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class HoaDonServiceImpl implements HoaDonService {
  @Autowired
    private HoaDonRepo hoaDonRepository;

    @Override
    public Map<String, Object> getHoaDonDetailsByMonth(int month, int year) {
        List<HoaDonE> hoaDons = hoaDonRepository.findByMonthAndYear(month, year);

        // Tính tổng doanh thu và số lượng hóa đơn
        double totalRevenue = hoaDons.stream().mapToDouble(HoaDonE::getSoTienThanhToan).sum();
        int totalCount = hoaDons.size();

        // Tạo DTO cho từng hóa đơn
        List<HoaDonDTO> hoaDonDTOs = hoaDons.stream().map(hoaDon -> {
            HoaDonDTO dto = new HoaDonDTO();
            dto.setMaHoaDon(hoaDon.getMaHoaDon());
            dto.setNgayTaoHoaDon(hoaDon.getNgayTaoHoaDon());
            dto.setSoTienThanhToan(hoaDon.getSoTienThanhToan());
            return dto;
        }).collect(Collectors.toList());

        // Chuẩn bị kết quả trả về
        Map<String, Object> result = new HashMap<>();
        result.put("totalRevenue", totalRevenue);
        result.put("totalCount", totalCount);
        result.put("hoaDons", hoaDonDTOs);

        return result;
    }

    @Override
    public Map<String, Object> getHoaDonDetailsByYear(int year) {
        List<HoaDonE> hoaDons = hoaDonRepository.findByYear(year);

        // Tính tổng doanh thu và số lượng hóa đơn
        double totalRevenue = hoaDons.stream().mapToDouble(HoaDonE::getSoTienThanhToan).sum();
        int totalCount = hoaDons.size();

        // Tạo DTO cho từng hóa đơn
        List<HoaDonDTO> hoaDonDTOs = hoaDons.stream().map(hoaDon -> {
            HoaDonDTO dto = new HoaDonDTO();
            dto.setMaHoaDon(hoaDon.getMaHoaDon());
            dto.setNgayTaoHoaDon(hoaDon.getNgayTaoHoaDon());
            dto.setSoTienThanhToan(hoaDon.getSoTienThanhToan());
            return dto;
        }).collect(Collectors.toList());

        // Chuẩn bị kết quả trả về
        Map<String, Object> result = new HashMap<>();
        result.put("totalRevenue", totalRevenue);
        result.put("totalCount", totalCount);
        result.put("hoaDons", hoaDonDTOs);

        return result;
    }


}
