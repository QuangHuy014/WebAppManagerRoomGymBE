package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhToanDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhToanE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhToanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api-public/thanhToan")
@RequiredArgsConstructor
public class ThanhToanAPI {

    private final ThanhToanService thanhToanService;

    @PostMapping
    public ResponseObject<?> createThanhToan(@RequestBody ThanhToanDTO thanhToanDTO){
        var result = new ResponseObject<>();
        try {
            Optional<ThanhToanE> thanhToanE = thanhToanService.saveThanhToan(thanhToanDTO);
            result.setData(thanhToanE);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages(BasicApiConstant.SUCCEED.name());
        } catch (Exception e) {
            log.info("fail when call api /api-public/thanhToan",e);
            throw new RuntimeException(e);
        }
        return result;
    }

}
