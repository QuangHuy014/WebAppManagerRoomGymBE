package com.codecrafter.WebAppManagerRoomGymBE.api;

import com.codecrafter.WebAppManagerRoomGymBE.configsecurity.security.UserPrincipal;
import com.codecrafter.WebAppManagerRoomGymBE.constant.common.BasicApiConstant;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LichSuTapLuyenE;
import com.codecrafter.WebAppManagerRoomGymBE.data.mgt.ResponseObject;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LichSuTapLuyenM;
import com.codecrafter.WebAppManagerRoomGymBE.service.LichSuTapLuyenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
            // Lấy thông tin UserPrincipal từ SecurityContext
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            // Lấy userId từ UserPrincipal và kiểm tra xem user có quyền truy cập không
            if (userPrincipal.getUserId() != maThanhVien) {
                result.setStatus(BasicApiConstant.FAILED.getStatus());
                result.setMessages("Không có quyền truy cập lịch sử tập luyện của người dùng khác.");
                return result;
            }

            // Gọi service để lấy danh sách lịch sử tập luyện
            List<LichSuTapLuyenM> lichSuList = lichSuTapLuyenService.getLichSuTapLuyenByThanhVienId(maThanhVien);
            result.setData(lichSuList);
            result.setStatus(BasicApiConstant.SUCCEED.getStatus());
            result.setMessages("Lấy lịch sử tập luyện thành công cho thành viên có mã " + maThanhVien);

        } catch (Exception e) {
            log.info("Lỗi khi gọi API /api-public/lichsutapluyen/thanhvien/{maThanhVien} với mã thành viên " + maThanhVien, e);
            result.setStatus(BasicApiConstant.FAILED.getStatus());
            result.setMessages("Đã xảy ra lỗi khi lấy lịch sử tập luyện.");
        }
        return result;
    }
}
