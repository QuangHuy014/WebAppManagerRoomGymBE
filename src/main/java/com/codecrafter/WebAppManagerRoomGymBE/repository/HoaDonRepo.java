package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HoaDonRepo extends JpaRepository <HoaDonE, Integer> {
     @Query("SELECT h FROM HoaDonE h WHERE DATE(h.ngayTaoHoaDon) = :date")
     List<HoaDonE> findByDate(Date date);

    @Query("SELECT h FROM HoaDonE h WHERE MONTH(h.ngayTaoHoaDon) = :month AND YEAR(h.ngayTaoHoaDon) = :year")
    List<HoaDonE> findByMonthAndYear(int month, int year);

    @Query("SELECT h FROM HoaDonE h WHERE YEAR(h.ngayTaoHoaDon) = :year")
    List<HoaDonE> findByYear(int year);
}



