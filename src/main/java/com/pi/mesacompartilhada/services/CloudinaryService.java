package com.pi.mesacompartilhada.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
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

    public Map uploadFile(byte[] file) {
        try {
            return cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        } catch(IOException e ) {
            throw new RuntimeException("Erro ao enviar arquivo", e);
        }
    }

    public Map deleteFile(String publicId) {
        try {
            return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch(IOException e) {
            throw new RuntimeException("Erro ao enviar arquivo", e);
        }
    }
}
