package com.bqt.newspaper.controller;

import com.bqt.newspaper.payload.FileResponse;
import com.bqt.newspaper.payload.Message;
import com.bqt.newspaper.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/api/v1/file/")
@RestController
@RequiredArgsConstructor
public class FileController {

    private final IFileService fileService;

    @Value("${spring.resources.static-locations}")
    private String path;

    @PostMapping("upload")
    public ResponseEntity<FileResponse> uploadFile(@RequestPart("files") MultipartFile[] files) throws IOException {
        List<String> list = new ArrayList<>();
        for(MultipartFile file : files){
            String fileResponse = fileService.uploadImage(path,file);
            list.add(fileResponse);
        }
        FileResponse fileResponse = new FileResponse(list);
        return ResponseEntity.status(HttpStatus.OK).body(fileResponse);
    }

    @DeleteMapping("delete/{fileName}")
    public ResponseEntity<Message> deleteFile(@PathVariable(name = "fileName") String fileName) throws IOException {
        Boolean status = fileService.deleteImage(path,fileName);
        return ResponseEntity.status(HttpStatus.OK).body(new Message(status));
    }

    @GetMapping("/getImage/{imageId}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageId) {
        try {
            Path imagePath = Paths.get(path, imageId);
            Resource resource = new UrlResource(imagePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
