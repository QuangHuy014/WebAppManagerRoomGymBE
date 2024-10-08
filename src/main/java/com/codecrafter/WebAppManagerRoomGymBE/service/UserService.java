package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UserDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;

import java.util.Optional;

public interface UserService {
  Optional<NguoiDungE> login(UserDTO userDTO);
}
