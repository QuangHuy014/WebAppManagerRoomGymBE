package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.GoiUuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.GoiUuDaiRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiTapService;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiUuDaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoiUuDaiServiceImpl implements GoiUuDaiService {
    @Autowired
    private GoiUuDaiRepo goiUuDaiRepo;

    @Override
    public List<GoiUuDaiE> getGoiUuDai() {
        return goiUuDaiRepo.findAll();
    }
    @Override
    public List<GoiUuDaiE> getGoiUuDaiByThanhVien(int maThanhVien) {
        return goiUuDaiRepo.findByMaThanhVien(maThanhVien);
    }
}
