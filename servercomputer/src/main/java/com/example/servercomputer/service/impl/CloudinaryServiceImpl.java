package com.example.servercomputer.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.servercomputer.exception.CustomRuntimeException;
import com.example.servercomputer.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    Cloudinary cloudinary;
    @Override
    public Map<?, ?> upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map<?, ?> resultMap = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return resultMap;
    }

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public File convert(MultipartFile multipartFile) {
        File file = new File(multipartFile.getOriginalFilename());
        try {
            FileOutputStream fOutputStream = new FileOutputStream(file);
            fOutputStream.write(multipartFile.getBytes());
            fOutputStream.close();
            return file;
        } catch (IOException e) {
            throw new CustomRuntimeException(e.getMessage(), e);
        }
    }
}
