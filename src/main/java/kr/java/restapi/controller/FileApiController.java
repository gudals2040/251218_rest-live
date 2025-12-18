package kr.java.restapi.controller;

import kr.java.restapi.model.dto.FileResponse;
import kr.java.restapi.model.entity.FileEntity;
import kr.java.restapi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

    // #(2)-8
    // LIST : GET /api/files -> List<FileResponse> & 200 OK
    @GetMapping
    public ResponseEntity<List<FileResponse>> findAll() {
        List<FileResponse> list = fileService.findAll();
        return ResponseEntity.ok(list);
    }

    // DOWNLOAD : GET /api/files/{id}/download -> 해당 파일 & 200
    @GetMapping("/{id}/download")
    // import org.springframework.core.io.Resource;
    public ResponseEntity<Resource> download(@PathVariable Long id) {
        FileEntity fileEntity = fileService.findById(id); // 메타데이터
        Resource resource = fileService.loadAsResource(id); // 파일 자체

        // 한글 파일명 인코딩
        String encodedFilename = URLEncoder.encode(
                fileEntity.getOriginalName(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        return ResponseEntity.ok()
                // import org.springframework.http.MediaType;
                .contentType(MediaType.parseMediaType(fileEntity.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encodedFilename)
                .body(resource);
    }
}
