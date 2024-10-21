package com.codecrafter.WebAppManagerRoomGymBE.service;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.ThanhVienM;


import java.util.Optional;

public interface ThanhVienService {
    Optional<ThanhVienM> register(ThanhVienDTO userDTO);
}
