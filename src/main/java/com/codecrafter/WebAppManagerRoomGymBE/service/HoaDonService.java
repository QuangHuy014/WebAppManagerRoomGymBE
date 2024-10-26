package com.codecrafter.WebAppManagerRoomGymBE.service;


import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

public interface HoaDonService {
    Map<String, Object> getHoaDonDetailsByMonth(int month, int year);
    Map<String, Object> getHoaDonDetailsByYear(int year);

}
