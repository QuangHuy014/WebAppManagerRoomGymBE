
package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LopHocM;
import com.codecrafter.WebAppManagerRoomGymBE.repository.LopHocRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.LopHocService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LopHocServiceImpl implements LopHocService {
    @Autowired
    private LopHocRepo lopHocRepo;


    @Override
    public List<LopHocM> getLopHocByThanhVienId(int maThanhVien) {
        List<LopHocE> lopHocEList = lopHocRepo.findByMaThanhVien(maThanhVien);
        return LopHocM.convertListLopHocEToLopHocM(lopHocEList);
    }
}
