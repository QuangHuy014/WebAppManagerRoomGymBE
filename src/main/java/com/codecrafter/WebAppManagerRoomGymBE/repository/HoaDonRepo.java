package com.codecrafter.WebAppManagerRoomGymBE.repository;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface HoaDonRepo extends JpaRepository <HoaDonE, Integer> {
       @Query("SELECT COUNT(h) FROM HoaDonE h WHERE DATE(h.ngayTaoHoaDon) = :date")
    long countByDate(Date date);

    @Query("SELECT COUNT(h) FROM HoaDonE h WHERE MONTH(h.ngayTaoHoaDon) = :month AND YEAR(h.ngayTaoHoaDon) = :year")
    long countByMonthAndYear(int month, int year);

    @Query("SELECT COUNT(h) FROM HoaDonE h WHERE YEAR(h.ngayTaoHoaDon) = :year")
    long countByYear(int year);

    @Query("SELECT SUM(h.soTienThanhToan) FROM HoaDonE h WHERE DATE(h.ngayTaoHoaDon) = :date")
    Double sumByDate(Date date);

    @Query("SELECT SUM(h.soTienThanhToan) FROM HoaDonE h WHERE MONTH(h.ngayTaoHoaDon) = :month AND YEAR(h.ngayTaoHoaDon) = :year")
    Double sumByMonthAndYear(int month, int year);

    @Query("SELECT SUM(h.soTienThanhToan) FROM HoaDonE h WHERE YEAR(h.ngayTaoHoaDon) = :year")
    Double sumByYear(int year);

}
