package com.codecrafter.WebAppManagerRoomGymBE.service;

import com.codecrafter.WebAppManagerRoomGymBE.data.dto.ThanhVienDTO;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;

public interface QrCodeService {
    String GenerateQrCode(ThanhVienDTO thanhVienDTO);
    String readQRCode(BufferedImage bufferedImage);
    String readQRCode (MultipartFile qrCodeFile);
    String readQRCode(String base64Image);
}
