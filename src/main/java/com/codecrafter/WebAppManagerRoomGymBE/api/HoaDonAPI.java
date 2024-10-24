package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.DangKyDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.HoaDonE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.service.HoaDonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api-public/registration")
public class HoaDonAPI {

    @Autowired
    private HoaDonService hoaDonService;

    @PostMapping
    public ResponseObject<Object> register(@RequestBody DangKyDTO registration) {
        var result = new ResponseObject<>();
        try {

            HoaDonE invoice = hoaDonService.register(registration);
            result.setData(invoice);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages(BasicApiConstant.SUCCEED.name());
        } catch (Exception e) {
            log.info("fail when call api /api-public/registration",e);
        }
        return result;
    }
}
