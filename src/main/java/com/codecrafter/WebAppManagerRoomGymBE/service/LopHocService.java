package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LopHocM;
import com.codecrafter.WebAppManagerRoomGymBE.repository.LopHocRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LopHocService {

    public List<LopHocM> getLopHocByThanhVienId(int maThanhVien);
}
