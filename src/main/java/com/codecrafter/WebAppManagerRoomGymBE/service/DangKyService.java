package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;

public interface DangKyService {

    DangKyE registerWithDiscount(int maThanhVien, int maGoiTap, int maGoiUuDai);

    DangKyE registerWithoutDiscount(int maThanhVien, int maGoiTap);
}
