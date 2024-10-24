package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhToanDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhToanE;

import java.util.Optional;

public interface ThanhToanService{
    Optional<ThanhToanE> saveThanhToan(ThanhToanDTO thanhToanDTO);
}
