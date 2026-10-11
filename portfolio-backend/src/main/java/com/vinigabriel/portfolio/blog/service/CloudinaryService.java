package com.vinigabriel.portfolio.blog.service;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                Map.of(
                        "folder", "portfolio/articles",
                        "resource_type", "image"
                )
        );

        return result.get("secure_url").toString();
    }


    public String uploadFile(MultipartFile file) throws IOException {
        Map<?, ?> result = cloudinary.uploader().upload(
                file.getBytes(),
                Map.of(
                        "folder", "portfolio/articles/files",
                        "resource_type", "raw",
                        "use_filename", true,
                        "unique_filename", true,
                        "filename_override", file.getOriginalFilename()
                )
        );

        return result.get("secure_url").toString();
    }

}