package com.pi.mesacompartilhada.services;

import java.util.Map;

public interface MediaStorageService {
    public Map uploadFile(byte[] file);
    public Map deleteFile(String publicId);
}
