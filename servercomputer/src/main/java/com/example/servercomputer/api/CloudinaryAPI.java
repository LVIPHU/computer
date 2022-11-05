package com.example.servercomputer.api;

import com.example.servercomputer.response.ResponseMessageDto;
import com.example.servercomputer.service.CloudinaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/upload")
public class CloudinaryAPI {
    @Autowired
    private CloudinaryService cloudDinaryService;
    @PostMapping("/cloudinary")
    public ResponseEntity<ResponseMessageDto> upLoad(@RequestParam("file") MultipartFile multipartFile)
            throws IOException {
        Map<?, ?> resultMap = cloudDinaryService.upload(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageDto(
                HttpStatus.OK, resultMap.get("url").toString(), LocalDateTime.now()));
    }
}
