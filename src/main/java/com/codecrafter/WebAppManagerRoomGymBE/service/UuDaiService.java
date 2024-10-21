package com.codecrafter.WebAppManagerRoomGymBE.service;


import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;

import java.util.List;

public interface UuDaiService {

    UuDaiE createUuDai(UuDaiE uuDai);

    UuDaiE getUuDaiById(int id);

    List<UuDaiE> getAllUuDais();

    List<UuDaiE> getActiveUuDais();

    UuDaiE updateUuDai(int id, UuDaiE uuDai);

    void deleteUuDai(int id);
}

