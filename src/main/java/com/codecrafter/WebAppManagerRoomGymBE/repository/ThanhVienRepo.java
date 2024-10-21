package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThanhVienRepo extends JpaRepository<ThanhVienE, Integer> {
    boolean existsByTenThanhVien(String tenThanhVien);
    boolean existsByEmailThanhVien(String emailThanhVien);
}
