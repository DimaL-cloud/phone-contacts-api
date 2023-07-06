package ua.dmytrolutsyuk.phonecontactsapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface ImageService {

    String saveImage(MultipartFile image, UUID uuid);

    void deleteImage(UUID uuid);
}
