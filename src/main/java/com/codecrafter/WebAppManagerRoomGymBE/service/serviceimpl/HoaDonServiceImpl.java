package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.DangKyDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.HoaDonDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private DangKyRepo dangKyRepository;

    @Autowired
    private HoaDonRepo hoaDonRepository;

    @Override
    public HoaDonE register(DangKyDTO registration) {
        HoaDonE invoice = new HoaDonE();
        invoice.setNgayTaoHoaDon(new Date());

        DangKyE dangKy = dangKyRepository.findById(registration.getMaDangKy())
                .orElseThrow(() -> new RuntimeException("Đăng ký không tồn tại"));

        invoice.setSoTienThanhToan(calculateTotalAmount(dangKy));

        HoaDonE savedInvoice = hoaDonRepository.save(invoice);

        dangKy.setHoaDon(savedInvoice);
        dangKyRepository.save(dangKy);

        return savedInvoice;
    }

    private float calculateTotalAmount(DangKyE dangKy) {

        float totalAmount = 0.0f;

        // Kiểm tra nếu có gói tập
        if (dangKy.getGoiUuDai() != null && dangKy.getGoiUuDai().getGoiTap() != null) {
            totalAmount += dangKy.getGoiUuDai().getGoiTap().getGiaGoiTap();
        }

        // Kiểm tra nếu có lớp học
        if (dangKy.getLopHoc() != null) {
            totalAmount += dangKy.getLopHoc().getGiaLopHoc();
        }

        return totalAmount;
    }


    @Override
    public List<HoaDonE> findAll() {
        List<HoaDonE> listHoaDon = hoaDonRepository.findAll();
        for (HoaDonE hoadon :listHoaDon)
        {
            hoadon.setTongHoaDon(listHoaDon.size());
        }
        return hoaDonRepository.findAll();
    }

    @Override
    public Optional<HoaDonE> findHoaDonById(int maHoaDon) {

        return hoaDonRepository.findById(maHoaDon);
    }

    @Override
    public HoaDonE updateHoaDon(int maHoaDon, HoaDonDTO hoaDonDTO) {

        return null;
    }
}
