package com.bqt.newspaper.service.impl;


import com.bqt.newspaper.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements IFileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        //File name: 1_abc.png
        String twoPrefix = file.getOriginalFilename().substring(0,2);

        //Random name generate file
        String randomId = UUID.randomUUID().toString();

        //concat twoPrefix + random
        String fileName = twoPrefix + randomId;

        //Full path
        String filePath = path + File.separator + fileName;

        // create folder if not created
        File f = new File(path);
        if( !f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }

    @Override
    public boolean deleteImage(String path, String fileName) throws IOException {
        try {
            String fullPath = path + fileName;
            Path file = Paths.get(fullPath);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


}
