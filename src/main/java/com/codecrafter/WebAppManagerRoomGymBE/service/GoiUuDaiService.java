package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.GoiUuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;

import java.util.List;

public interface GoiUuDaiService {
       List<GoiUuDaiE> getGoiUuDai();
      List<GoiUuDaiE> getGoiUuDaiByThanhVien(int maThanhVien);
}