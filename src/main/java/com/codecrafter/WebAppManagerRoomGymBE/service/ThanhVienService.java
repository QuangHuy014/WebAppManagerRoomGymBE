package com.codecrafter.WebAppManagerRoomGymBE.service;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import org.springframework.data.domain.Page;


import java.util.Optional;

public interface ThanhVienService {
     Optional<ThanhVienE> register(ThanhVienDTO userDTO);
      Optional<ThanhVienE> login(ThanhVienDTO memberDTO);
    public Page<ThanhVienE> getAllMembers(int page, int size, String sortBy, boolean ascending);
        Optional<ThanhVienE> disableMember(int maThanhVien);
}
