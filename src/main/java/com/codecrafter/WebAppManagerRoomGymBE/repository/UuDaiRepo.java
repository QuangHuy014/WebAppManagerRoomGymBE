package com.codecrafter.WebAppManagerRoomGymBE.repository;


import com.codecrafter.WebAppManagerRoomGymBE.data.entity.UuDaiE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UuDaiRepo extends JpaRepository<UuDaiE, Integer> {

    //tim kiem ma giam gia dang active
    List<UuDaiE> findByTrangThaiUuDai(boolean trangThaiUuDai);
}
