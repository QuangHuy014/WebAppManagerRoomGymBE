package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.UuDaiRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.UuDaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UuDaiServiceImpl implements UuDaiService {

    @Autowired
    private UuDaiRepo uuDaiRepository;

    @Override
    public UuDaiE createUuDai(UuDaiDTO uuDai) {
        UuDaiE uuDaiE = UuDaiE.builder()
                .moTaUuDai(uuDai.getMoTaUuDai())
                .giaTriUuDai(uuDai.getGiaTriUuDai())
                .ngayBatDauUuDai(uuDai.getNgayBatDauUuDai())
                .ngayKetThucUuDai(uuDai.getNgayKetThucUuDai())
                .trangThaiUuDai(uuDai.isTrangThaiUuDai())
                .build();
        return uuDaiRepository.save(uuDaiE);
    }

    @Override
    public UuDaiE getUuDaiById(int id) {
        return uuDaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + id));
    }

    @Override
    public List<UuDaiE> getAllUuDais() {
        List<UuDaiE> listUuDai = uuDaiRepository.findAll();
        for (UuDaiE uuDai :listUuDai)
        {
            uuDai.setTongUuDai(listUuDai.size());
        }
        return uuDaiRepository.findAll();
    }

    @Override
    public List<UuDaiE> getActiveUuDais() {
        return uuDaiRepository.findByTrangThaiUuDai(true);
    }

    @Override
    public UuDaiE updateUuDai(int id, UuDaiDTO uuDai) {
        UuDaiE existingUuDai = getUuDaiById(id);
        existingUuDai.setMoTaUuDai(uuDai.getMoTaUuDai());
        existingUuDai.setNgayBatDauUuDai(uuDai.getNgayBatDauUuDai());
        existingUuDai.setNgayKetThucUuDai(uuDai.getNgayKetThucUuDai());
        existingUuDai.setGiaTriUuDai(uuDai.getGiaTriUuDai());
        existingUuDai.setTrangThaiUuDai(uuDai.isTrangThaiUuDai());
        return uuDaiRepository.save(existingUuDai);
    }

    @Override
    public void deleteUuDai(int id) {
        uuDaiRepository.deleteById(id);
    }
}
