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
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.GoiTapE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.NguoiDungE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.ThanhVienE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.VaiTroE;
import com.codecrafter.WebAppManagerRoomGymBE.service.GoiTapService;
import com.codecrafter.WebAppManagerRoomGymBE.service.NguoiDungService;
import com.codecrafter.WebAppManagerRoomGymBE.service.SendMailService;
import com.codecrafter.WebAppManagerRoomGymBE.service.ThanhVienService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private GoiTapService goiTapService;
    @Autowired
    private SendMailService sendMailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public NguoiDungAPI(JwtIssuer jwtIssuer) {
        this.jwtIssuer = jwtIssuer;
    }


    @PostMapping("/register") // Endpoint cho đăng ký khách hàng
    public ResponseEntity<RegisterResponse> register(@RequestBody ThanhVienDTO thanhVienDTO, @RequestParam int maGoiTap) {
        // Kiểm tra xem tên khách hàng có rỗng không
        if (thanhVienDTO.getTenThanhVien() == null || thanhVienDTO.getTenThanhVien().isEmpty()) {
            return ResponseEntity.status(400).body(RegisterResponse.builder()
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Tên khách hàng không được để trống.")
                    .build());
        }

        // Kiểm tra xem mật khẩu có rỗng không
        if (thanhVienDTO.getMatKhauNguoiDung() == null || thanhVienDTO.getMatKhauNguoiDung().isEmpty()) {
            return ResponseEntity.status(400).body(RegisterResponse.builder()
                    .status(BasicApiConstant.FAILED.getStatus())
                    .description("Mật khẩu không được để trống.")
                    .build());
        }

        // Đăng ký khách hàng
        Optional<ThanhVienE> registeredMember = thanhVienService.register(thanhVienDTO, maGoiTap);
        if (registeredMember.isEmpty()) {
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
            if (!passwordEncoder.matches(userDTO.getMatKhauNguoiDung(), nguoiDung.getMatKhauNguoiDung())) {
                return ResponseEntity.status(401).body(LoginResponse.builder()
                        .accessToken(null)
                        .status(BasicApiConstant.FAILED.getStatus())
                        .description(LoginStatus.FAILED_PASSWORD.getStatusDescription())
                        .build());
            }

            // Lấy thông tin vai trò
            VaiTroE vaiTro = nguoiDung.getVaiTro();
            String role = vaiTro != null ? vaiTro.getTenVaiTro() : null; // Kiểm tra nếu vai trò không null

            var requestBuilder = JwtIssuer.Request.builder()
                    .userId((long) nguoiDung.getMaNguoiDung())
                    .username(nguoiDung.getTenNguoiDung())
                    .roles(List.of(role))
                    .build();

            var token = jwtIssuer.issue(requestBuilder);

            // Tạo LoginResponse với tenNguoiDung và vai trò
            LoginResponse response = LoginResponse.builder()
                    .accessToken(token)
                    .tenNguoiDung(nguoiDung.getTenNguoiDung()) // Thêm tenNguoiDung
                    .role(role) // Thêm vai trò
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


