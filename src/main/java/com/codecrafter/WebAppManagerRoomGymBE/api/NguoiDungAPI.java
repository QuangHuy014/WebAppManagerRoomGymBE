package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtIssuer;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginResponse;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.RegisterResponse;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.LoginStatus;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.RegisterStatus;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
public class NguoiDungAPI {

    @Autowired
    private NguoiDungService nguoiDungService;
    @Autowired
    private final JwtIssuer jwtIssuer;
    public NguoiDungAPI(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

   @PostMapping("/register")
public ResponseEntity<RegisterResponse> register(@RequestBody NguoiDungDTO userDTO) {
    // Kiểm tra xem tên người dùng có rỗng không
    if (userDTO.getTenNguoiDung() == null || userDTO.getTenNguoiDung().isEmpty()) {
        return ResponseEntity.status(400).body(RegisterResponse.builder()
                .status(BasicApiConstant.FAILED.getStatus())
                .description("Tên người dùng không được để trống.")
                .build());
    }

    // Kiểm tra xem mật khẩu có rỗng không
    if (userDTO.getMatKhauNguoiDung() == null || userDTO.getMatKhauNguoiDung().isEmpty()) {
        return ResponseEntity.status(400).body(RegisterResponse.builder()
                .status(BasicApiConstant.FAILED.getStatus())
                .description("Mật khẩu không được để trống.")
                .build());
    }

    // Đăng ký người dùng
    Optional<NguoiDungE> registeredUser = nguoiDungService.register(userDTO);
    if (registeredUser.isEmpty()) {
        return ResponseEntity.status(400).body(RegisterResponse.builder()
                .status(BasicApiConstant.FAILED.getStatus())
                .description(RegisterStatus.ACCOUNT_EXISTED.getStateDescription())
                .build());
    }

    // Nếu đăng ký thành công
    return ResponseEntity.ok(RegisterResponse.builder()
            .status(BasicApiConstant.SUCCEED.getStatus())
            .description(RegisterStatus.SUCCEED.getStateDescription())
            .build());
}


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody NguoiDungDTO userDTO) {
        if (userDTO.getTenNguoiDung() == null || userDTO.getTenNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(LoginResponse.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Tên người dùng không được để trống")
                    .build());
        }
        if (userDTO.getMatKhauNguoiDung() == null || userDTO.getMatKhauNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(LoginResponse.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Mật khẩu không được để trống")
                    .build());
        }

        Optional<NguoiDungE> user = nguoiDungService.login(userDTO);
        if (user.isPresent()) {
            NguoiDungE nguoiDung = user.get();
            if (!nguoiDung.getMatKhauNguoiDung().equals(userDTO.getMatKhauNguoiDung())) {
                return ResponseEntity.status(401).body(LoginResponse.builder()
                        .accessToken(null)
                        .status(BasicApiConstant.FAILED.getStatus())
                        .description(LoginStatus.FAILED_PASSWORD.getStatusDescription())
                        .build());
            }
            int maVaiTro = nguoiDung.getVaiTro().getMaVaiTro();
            String role = maVaiTro == 2 ? "Nhân viên" : "Khách hàng"; // Điều chỉnh ở đây
            var requestBuilder = JwtIssuer.Request.builder()
                    .userId((long) nguoiDung.getMaNguoiDung())
                    .username(nguoiDung.getTenNguoiDung())
                    .roles(List.of(role))
                    .build();
            var token = jwtIssuer.issue(requestBuilder);
            LoginResponse response = LoginResponse.builder()
                    .accessToken(token)
                    .status(BasicApiConstant.SUCCEED.getStatus())
                    .description(LoginStatus.SUCCEDD.getStatusDescription())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(LoginResponse.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(LoginStatus.NOT_EXIST.getStatusDescription())
                    .build());
        }
    }
}