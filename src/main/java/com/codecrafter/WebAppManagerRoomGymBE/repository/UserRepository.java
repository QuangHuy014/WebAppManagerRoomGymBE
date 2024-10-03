package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<NguoiDungE, Integer> {
    NguoiDungE findByTenNguoiDung(String tenNguoiDung);
}