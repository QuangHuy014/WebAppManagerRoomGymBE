package com.codecrafter.WebAppManagerRoomGymBE.repository;


import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoaDonRepo extends JpaRepository<HoaDonE, Integer> {
}

