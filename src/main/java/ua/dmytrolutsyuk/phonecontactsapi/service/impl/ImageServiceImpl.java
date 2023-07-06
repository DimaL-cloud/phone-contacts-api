package ua.dmytrolutsyuk.phonecontactsapi.service.impl;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.BlobItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ImageNotFoundException;
import ua.dmytrolutsyuk.phonecontactsapi.exception.ImageUploadException;
import ua.dmytrolutsyuk.phonecontactsapi.service.ImageService;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final BlobContainerClient imageContainerClient;

    @Override
    public String saveImage(MultipartFile image, UUID uuid) {
        String imageName = uuid + "-" + image.getOriginalFilename();

        BlobClient blobClient = imageContainerClient.getBlobClient(imageName);
        uploadImage(blobClient, image);

        return blobClient.getBlobUrl();
    }

    private void uploadImage(BlobClient blobClient, MultipartFile image) {
        try {
            blobClient.upload(image.getInputStream(), image.getSize(), true);
        } catch (IOException e) {
            throw new ImageUploadException(image.getName());
        }
    }

    @Override
    public void deleteImage(UUID uuid) {
        BlobItem deletedBlobItem = imageContainerClient
                .listBlobs()
                .stream()
                .filter(blobItem -> blobItem.getName().contains(uuid.toString()))
                .findAny()
                .orElseThrow(ImageNotFoundException::new);
    }
}
