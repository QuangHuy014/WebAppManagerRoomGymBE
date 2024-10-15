//package com.codecrafter.WebAppManagerRoomGymBE.repository;
//
//import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface LopHocRepo extends JpaRepository<LopHocE, Integer> {
//
//    List<LopHocE> findByMaThanhVien(int maThanhVien);
////    @Query("SELECT lh FROM LopHocE lh JOIN DangKyE dk ON lh.maLopHoc = dk.maLopHoc WHERE dk.maThanhVien = :maThanhVien")
////    List<LopHocE> findLopHocByThanhVienId(@Param("maThanhVien") int maThanhVien);
//
//}
