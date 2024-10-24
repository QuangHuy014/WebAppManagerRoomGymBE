package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.data.dto.DangKyDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;

public interface HoaDonService {
    HoaDonE register(DangKyDTO registration);
}
