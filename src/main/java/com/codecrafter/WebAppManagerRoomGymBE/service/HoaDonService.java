package com.codecrafter.WebAppManagerRoomGymBE.service;

import java.util.Date;

public interface HoaDonService {
    long countHoaDonByDate(Date date);
    long countHoaDonByMonth(int month, int year);
    long countHoaDonByYear(int year);

    Double getDoanhThuByDate(Date date);
    Double getDoanhThuByMonth(int month, int year);
    Double getDoanhThuByYear(int year);


}
