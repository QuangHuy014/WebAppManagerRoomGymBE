package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.data.dto.UuDaiDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;

import java.util.List;

public interface UuDaiService {

    UuDaiE createUuDai(UuDaiDTO uuDai);

    UuDaiE getUuDaiById(int id);

    List<UuDaiE> getAllUuDais();

    List<UuDaiE> getActiveUuDais();

    UuDaiE updateUuDai(int id, UuDaiDTO uuDaiDTO);

    void deleteUuDai(int id);
}

