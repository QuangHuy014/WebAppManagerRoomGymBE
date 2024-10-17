package com.codecrafter.WebAppManagerRoomGymBE.api;


import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.service.LopHocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api-public/lophoc")
public class LopHocAPI {

    @Autowired
    private  LopHocService lopHocService;

    @GetMapping("/getLopHocByThanhVien")
    public ResponseObject<?> doGetLopHocByThanhVien(@RequestParam int maThanhVien) {
        var result = new ResponseObject<>();

        try {
            result.setData(lopHocService.getLopHocByThanhVienId(maThanhVien));
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("get lop hoc by ma thanh vien " + maThanhVien + " thanh cong");
        } catch (Exception e) {
            log.info("fail when call api /api-public/lophoc/getLopHocByThanhVien", e);
        }
        return result;

    }
}
