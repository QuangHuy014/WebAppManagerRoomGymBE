package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ThanhVienRepository extends JpaRepository<ThanhVienE, Integer> {
    boolean existsByTenThanhVien(String tenThanhVien);
    boolean existsByEmailThanhVien(String emailThanhVien);
}
