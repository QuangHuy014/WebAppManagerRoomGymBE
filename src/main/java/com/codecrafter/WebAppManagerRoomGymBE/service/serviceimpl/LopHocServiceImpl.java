
package com.codecrafter.WebAppManagerRoomGymBE.service.serviceimpl;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.LopHocDTO;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.DangKyE;
import com.codecrafter.WebAppManagerRoomGymBE.data.entity.LopHocE;
import com.codecrafter.WebAppManagerRoomGymBE.data.model.LopHocM;
import com.codecrafter.WebAppManagerRoomGymBE.repository.DangKyRepo;
import com.codecrafter.WebAppManagerRoomGymBE.repository.LopHocRepo;
import com.codecrafter.WebAppManagerRoomGymBE.service.LopHocService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LopHocServiceImpl implements LopHocService {
    private final LopHocRepo lopHocRepo;
    @Autowired
    private DangKyRepo dangKyRepo;


    @Override
    public List<LopHocM> getAllLopHoc() {
        List<LopHocE> lopHocEList = lopHocRepo.findAll();
        return LopHocM.convertListLopHocEToLopHocM(lopHocEList);
    }

    @Override
    public List<LopHocM> getLopHocByThanhVienId(int maThanhVien) {
        List<LopHocE> lopHocEList = lopHocRepo.findByMaThanhVien(maThanhVien);
        return LopHocM.convertListLopHocEToLopHocM(lopHocEList);
    }

    @Override
    public List<LopHocE> getLopHocByParams(Integer maLopHoc, String tenLopHoc, String moTaLopHoc, Float giaLopHoc, Date lichHoc) {
        return lopHocRepo.findAll((Root<LopHocE> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>(); // Sử dụng đúng Predicate

            // Điều kiện cho các tham số
            if (maLopHoc != null) {
                predicates.add(cb.equal(root.get("maLopHoc"), maLopHoc));
            }
            if (tenLopHoc != null) {
                predicates.add(cb.like(root.get("tenLopHoc"), "%" + tenLopHoc + "%")); // Sử dụng LIKE để tìm kiếm
            }
            if (moTaLopHoc != null) {
                predicates.add(cb.like(root.get("moTaLopHoc"), "%" + moTaLopHoc + "%")); // Sử dụng LIKE để tìm kiếm
            }
            if (giaLopHoc != null) {
                predicates.add(cb.equal(root.get("giaLopHoc"), giaLopHoc));
            }
            if (lichHoc != null) {
                predicates.add(cb.equal(root.get("lichHoc"), lichHoc));
            }

            // Kết hợp các điều kiện
            return cb.and(predicates.toArray(new jakarta.persistence.criteria.Predicate[0]));
        });
    }


    @Override
    public LopHocE addLopHoc(LopHocDTO lopHocDTO) {
        LopHocE lopHocE = new LopHocE();
        lopHocE.setTenLopHoc(lopHocDTO.getTenLopHoc());
        lopHocE.setMoTaLopHoc(lopHocDTO.getMoTaLopHoc());
        lopHocE.setGiaLopHoc(lopHocDTO.getGiaLopHoc());
        lopHocE.setLichHoc(lopHocDTO.getLichHoc());
        lopHocE.setSoLuongThanhVienLopHoc(lopHocDTO.getSoLuongThanhVienLopHoc());
        return lopHocRepo.save(lopHocE);
    }

    @Override
    public LopHocE updateLopHoc(int maLopHoc, LopHocDTO lopHocDTO) {
        Optional<LopHocE> lopHocOpt = lopHocRepo.findById(maLopHoc);
        if (lopHocOpt.isPresent()) {
            LopHocE lopHocE = lopHocOpt.get();
            lopHocE.setTenLopHoc(lopHocDTO.getTenLopHoc());
            lopHocE.setMoTaLopHoc(lopHocDTO.getMoTaLopHoc());
            lopHocE.setGiaLopHoc(lopHocDTO.getGiaLopHoc());
            lopHocE.setLichHoc(lopHocDTO.getLichHoc());
            lopHocE.setSoLuongThanhVienLopHoc(lopHocDTO.getSoLuongThanhVienLopHoc());
            return lopHocRepo.save(lopHocE);
        }
        return null;
    }

    @Override
    public void deleteLopHoc(int maLopHoc) {
        lopHocRepo.deleteById(maLopHoc);
    }

}
