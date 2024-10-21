package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.NguoiDungM;


import java.util.Optional;

public interface NguoiDungService {
  Optional<NguoiDungM> login(NguoiDungDTO userDTO);
}
