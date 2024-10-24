package com.codecrafter.WebAppManagerRoomGymBE.service;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;


import java.util.Optional;

public interface ThanhVienService {
     Optional<ThanhVienE> register(ThanhVienDTO userDTO, int maGoiTap);
      Optional<ThanhVienE> login(ThanhVienDTO memberDTO);

}
