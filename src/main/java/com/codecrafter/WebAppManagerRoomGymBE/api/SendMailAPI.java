package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMailAPI {
      @Autowired
    private SendMailService sendEmailService; // Sử dụng EmailService

    @PostMapping("/send-email")
    public String register(@RequestBody ThanhVienDTO thanhVienDTO) {
        try {
            sendEmailService.sendEmail(thanhVienDTO);
            return BasicApiConstant.SUCCEED.getStatus();
        } catch (Exception e) {
            return BasicApiConstant.FAILED.getStatus();
        }
    }
}
