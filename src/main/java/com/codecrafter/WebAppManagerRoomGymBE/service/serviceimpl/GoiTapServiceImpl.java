package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.repository.GoiTapRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiTapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoiTapServiceImpl implements GoiTapService {
      @Autowired
    private GoiTapRepo goiTapRepository;

    @Override
    public Optional<GoiTapE> getGoiTapById(int maGoiTap) {
        return goiTapRepository.findById(maGoiTap);
    }

}
