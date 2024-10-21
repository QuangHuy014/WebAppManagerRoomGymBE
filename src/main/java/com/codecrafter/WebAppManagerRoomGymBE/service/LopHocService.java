
package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.model.LopHocM;

import java.util.List;

public interface LopHocService {

    public List<LopHocM> getLopHocByThanhVienId(int maThanhVien);
}
