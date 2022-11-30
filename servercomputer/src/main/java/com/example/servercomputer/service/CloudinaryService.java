package com.example.servercomputer.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<?, ?> upload(MultipartFile multipartFile) throws IOException;

    File convert(MultipartFile multipartFile);
}
