package com.codecrafter.WebAppManagerRoomGymBE.service;


import java.util.Date;

public interface HoaDonService {
     long countHoaDonByDate(Date date);
    long countHoaDonByMonth(int month, int year);
    long countHoaDonByYear(int year);

    Double calculateRevenueByDate(Date date);
    Double calculateRevenueByMonth(int month, int year);
    Double calculateRevenueByYear(int year);

}
