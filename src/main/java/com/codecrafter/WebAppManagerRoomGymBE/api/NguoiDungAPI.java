package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.jwt.JwtIssuer;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.LoginResponse;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.model.RegisterResponse;
import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.UserPrincipal;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.LoginStatus;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.RegisterStatus;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.NguoiDungDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.NguoiDungM;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.ThanhVienM;
import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhVienService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private ThanhVienService thanhVienService;

    public NguoiDungAPI(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }

   @PostMapping("/register")
public ResponseEntity<RegisterResponse> register(@RequestBody ThanhVienDTO userDTO) {
    if (userDTO.getTenThanhVien() == null || userDTO.getTenThanhVien().isEmpty()) {
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
    Optional<ThanhVienM> registeredUser = thanhVienService.register(userDTO);
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
        // Kiểm tra tên người dùng
        if (userDTO.getTenNguoiDung() == null || userDTO.getTenNguoiDung().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponse.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Tên người dùng không được để trống")
                    .build());
        }

        // Kiểm tra mật khẩu
        if (userDTO.getMatKhauNguoiDung() == null || userDTO.getMatKhauNguoiDung().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(LoginResponse.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Mật khẩu không được để trống")
                    .build());
        }

        Optional<NguoiDungM> user = nguoiDungService.login(userDTO);
        if (user.isPresent()) {
            NguoiDungM nguoiDung = user.get();

            // Kiểm tra mật khẩu
            if (!nguoiDung.getMatKhauNguoiDung().equals(userDTO.getMatKhauNguoiDung())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoginResponse.builder()
                        .accessToken(null)
                        .status(BasicApiConstant.FAILED.getStatus())
                        .description(LoginStatus.FAILED_PASSWORD.getStatusDescription())
                        .build());
            }

            // Lấy mã vai trò từ đối tượng nguoiDung
            int role = nguoiDung.getMaVaiTro(); // role là kiểu int

            // Tạo token
            var requestBuilder = JwtIssuer.Request.builder()
                    .userId((long) nguoiDung.getMaNguoiDung())
                    .username(nguoiDung.getTenNguoiDung())
                    .roles(List.of(String.valueOf(role))) // Chuyển đổi role thành String
                    .build();
            var token = jwtIssuer.issue(requestBuilder);

            LoginResponse response = LoginResponse.builder()
                    .accessToken(token)
                    .status(BasicApiConstant.SUCCEED.getStatus())
                    .description(LoginStatus.SUCCEDD.getStatusDescription())
                    .build();
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(LoginResponse.builder()
                    .accessToken(null)
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description(LoginStatus.NOT_EXIST.getStatusDescription())
                    .build());
        }
    }
}


