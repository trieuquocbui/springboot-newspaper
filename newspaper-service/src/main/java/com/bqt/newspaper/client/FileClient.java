package com.bqt.newspaper.client;

import com.bqt.newspaper.configuration.CustomFeignClientConfiguration;
import com.bqt.newspaper.payload.FileResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

//@FeignClient(name = "file", url = "http://api-gateway:9191/api/v1/file/", configuration = CustomFeignClientConfiguration.class)
@FeignClient(name = "file", url = "http://localhost:9191/api/v1/file/", configuration = CustomFeignClientConfiguration.class)
public interface FileClient {

    @PostMapping(value = "upload",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<FileResponse> uploadFiles(@RequestPart("files") MultipartFile[] files);
}
