package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.DangKyDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private DangKyRepo dangKyRepository;

    @Autowired
    private HoaDonRepo hoaDonRepository;

    @Override
    public HoaDonE register(DangKyDTO registration) {
        // Tạo hóa đơn cho đăng ký
        HoaDonE invoice = new HoaDonE();
        invoice.setNgayTaoHoaDon(new Date());

        // Lấy thông tin đăng ký từ ma_dang_ky
        DangKyE dangKy = dangKyRepository.findById(registration.getMaDangKy())
                .orElseThrow(() -> new RuntimeException("Đăng ký không tồn tại"));

        // Tính tổng số tiền từ đăng ký
        invoice.setSoTienThanhToan(calculateTotalAmount(dangKy));

        HoaDonE savedInvoice = hoaDonRepository.save(invoice);

        dangKy.setHoaDon(savedInvoice);
        dangKyRepository.save(dangKy);

        return savedInvoice; // Trả về hóa đơn đã lưu
    }


    // Hàm tính tổng số tiền từ thông tin đăng ký
    private float calculateTotalAmount(DangKyE dangKy) {
        float totalAmount = 0.0f;
        totalAmount = dangKy.getGoiUuDai().getGoiTap().getGiaGoiTap();

//         totalAmount = dangKy.getLopHoc().getSoLuong() * dangKy.getGoiUuDai().getDonGia();

        return totalAmount;
    }
}
