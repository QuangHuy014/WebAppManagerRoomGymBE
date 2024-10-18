package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.model.LichSuTapLuyenM;

import java.util.List;


public interface LichSuTapLuyenService {

    public List<LichSuTapLuyenM> getLichSuTapLuyenByThanhVienId(int maThanhVien);

    }
