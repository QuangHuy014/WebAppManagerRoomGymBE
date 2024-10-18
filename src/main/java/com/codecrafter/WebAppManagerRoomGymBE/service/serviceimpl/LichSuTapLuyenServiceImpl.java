package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LichSuTapLuyenE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LichSuTapLuyenM;
import com.codecrafter.WebAppManagerRoomGymBE.repository.LichSuTapLuyenRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.LichSuTapLuyenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuTapLuyenServiceImpl implements LichSuTapLuyenService {

    @Autowired
    private LichSuTapLuyenRepo lichSuTapLuyenRepo;

    @Override
    public List<LichSuTapLuyenM> getLichSuTapLuyenByThanhVienId(int maThanhVien) {
        List<LichSuTapLuyenE> listLichSuTapLuyen = lichSuTapLuyenRepo.findByThanhVien_MaThanhVien(maThanhVien);
        return LichSuTapLuyenM.convertListLichSuTapLuyenEToListLichSuTapLuyenM(listLichSuTapLuyen);
    }
}
