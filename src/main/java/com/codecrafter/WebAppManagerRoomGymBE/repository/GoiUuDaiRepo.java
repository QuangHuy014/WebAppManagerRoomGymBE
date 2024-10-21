package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiUuDaiE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.util.Optional;

@Repository
public interface GoiUuDaiRepo extends JpaRepository<GoiUuDaiE, Integer> {

    Optional<GoiUuDaiE> findByMaGoiUuDai(int maGoiUuDai);
}

