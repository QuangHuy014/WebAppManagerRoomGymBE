package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.RegisterResponse;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.RegisterStatus;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api-public/register")
public class RegisterAPI {

    @Autowired
    private NguoiDungService nguoiDungService;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@RequestBody NguoiDungDTO userDTO) {
        // Kiểm tra thông tin đầu vào
        if (userDTO.getTenNguoiDung() == null || userDTO.getTenNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(RegisterResponse.builder()
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(RegisterStatus.FAILED.getStateDescription())
                    .build());
        }

        if (userDTO.getMatKhauNguoiDung() == null || userDTO.getMatKhauNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(RegisterResponse.builder()
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Mật khẩu không được để trống.")
                    .build());
        }

        // Kiểm tra người dùng đã tồn tại
        Optional<NguoiDungE> existingUser = nguoiDungService.register(userDTO);
        if (existingUser.isPresent()) {
            return ResponseEntity.status(400).body(RegisterResponse.builder()
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(RegisterStatus.ACCOUNT_EXISTED.getStateDescription())
                    .build());
        }

        // Đăng ký người dùng mới
        return ResponseEntity.ok(RegisterResponse.builder()
                .status(BasicApiConstant.SUCCEED.getStatus())
                .description(RegisterStatus.SUCCEED.getStateDescription())
                .build());
    }
}
