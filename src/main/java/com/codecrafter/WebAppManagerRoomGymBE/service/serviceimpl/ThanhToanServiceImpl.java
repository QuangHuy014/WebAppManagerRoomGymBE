package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhToanDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhToanE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.HoaDonRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.ThanhToanRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhToanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ThanhToanServiceImpl implements ThanhToanService {

    private final HoaDonRepo hoaDonRepo;
    private final ThanhToanRepo thanhToanRepo;

    @Override
    public Optional<ThanhToanE> saveThanhToan(ThanhToanDTO thanhToanDTO) {
        Optional<HoaDonE> hoaDonE = hoaDonRepo.findById(thanhToanDTO.getMaHoaDon());
        ThanhToanE thanhToanE = new ThanhToanE();
        thanhToanE.setHoaDon(hoaDonE.get());
        thanhToanE.setMoTaThanhToan(thanhToanDTO.getMoTaThanhToan());
        thanhToanE.setNgayThanhToan(new Date());
        thanhToanE.setSoTienThanhToan(thanhToanDTO.getSoTienThanhToan());
        return Optional.of(thanhToanRepo.save(thanhToanE));
    }
}
