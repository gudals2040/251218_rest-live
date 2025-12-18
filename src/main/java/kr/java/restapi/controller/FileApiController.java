package kr.java.restapi.controller;

import kr.java.restapi.model.dto.FileResponse;
import kr.java.restapi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

// #(2)-5
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileApiController {

    private final FileService fileService;

    // UPLOAD : POST /api/files -> 201
    @PostMapping
    public ResponseEntity<FileResponse> upload(
            @RequestParam MultipartFile file
            ) {
        FileResponse response = fileService.upload(file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
