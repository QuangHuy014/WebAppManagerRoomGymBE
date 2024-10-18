package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LichSuTapLuyenE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LichSuTapLuyenM;
import com.codecrafter.WebAppManagerRoomGymBE.service.LichSuTapLuyenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api-public/lichsutapluyen")
public class LichSuTapLuyenAPI {

    @Autowired
    private LichSuTapLuyenService lichSuTapLuyenService;

    @GetMapping("/thanhvien/{maThanhVien}")
    public ResponseObject<?> getLichSuTapLuyen(@PathVariable int maThanhVien) {
        var result = new ResponseObject<>();
        try {
            List<LichSuTapLuyenM> lichSuList = lichSuTapLuyenService.getLichSuTapLuyenByThanhVienId(maThanhVien);
            result.setData(lichSuList);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("get lich su tap luyen co ma thanh vien "+maThanhVien+" thanh cong");
        } catch (Exception e) {
            log.info("fail when call api /api-public/lichsutapluyen/thanhvien/{maThanhVien} voi ma thanh vien "+maThanhVien,e);
        }
        return result;
    }
}
