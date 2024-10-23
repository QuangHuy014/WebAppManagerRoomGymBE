package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

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
    public UuDaiE createUuDai(UuDaiE uuDai) {
        return uuDaiRepository.save(uuDai);
    }

    @Override
    public UuDaiE getUuDaiById(int id) {
        return uuDaiRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Discount not found with id: " + id));
    }

    @Override
    public List<UuDaiE> getAllUuDais() {
        return uuDaiRepository.findAll();
    }

    @Override
    public List<UuDaiE> getActiveUuDais() {
        return uuDaiRepository.findByTrangThaiUuDai(true);
    }

    @Override
    public UuDaiE updateUuDai(int id, UuDaiE uuDai) {
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