package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;

import java.util.Optional;

public interface GoiTapService {
     Optional<GoiTapE> getGoiTapById(int maGoiTap);
}
