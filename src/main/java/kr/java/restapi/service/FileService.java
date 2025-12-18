package kr.java.restapi.service;

import jakarta.annotation.PostConstruct;
import kr.java.restapi.model.dto.FileResponse;
import kr.java.restapi.model.entity.FileEntity;
import kr.java.restapi.model.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

// #(2)-4
@Service
@RequiredArgsConstructor
// import org.springframework.transaction.annotation.Transactional;
@Transactional(readOnly = true)
@Slf4j // 로그 처리
public class FileService {

    private final FileRepository fileRepository;

    // import org.springframework.beans.factory.annotation.Value;
    @Value("${file.upload-dir}")
    private String uploadDir;

    // 허용할 타입
    private static final Set<String> ALLOWED_TYPES = Set.of(
      "image/jpeg", "image/png", "image/gif",
        "application/pdf", "text/plain"
    );

    // 초기화 : 업로드 디렉토리 설정
    @PostConstruct
    public void init() {
        // import java.nio.file.Path;
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("업로드 디렉토리 생성 실패");
        }
    }

    // 파일 검증
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new IllegalArgumentException("허용되지 않는 파일 형식");
        }
    }

    // 확장자 추출
    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }

    // UPLOAD
    @Transactional
    public FileResponse upload(MultipartFile file) {
        // 1. 검증
        validateFile(file);

        // 2. UUID 파일명 (한글, 해킹 관련...)
        String originalName = file.getOriginalFilename();
        String extension = extractExtension(originalName);
        String storedName = UUID.randomUUID() + extension;

        // 3. 저장 경로
        Path filePath = Paths.get(uploadDir).resolve(storedName);

        try {
            // 4. 파일 저장
            Files.copy(file.getInputStream(), filePath,
                    StandardCopyOption.REPLACE_EXISTING);
            // 5. 메타데이터 DB 저장
            FileEntity fileEntity = FileEntity.builder()
                    .originalName(originalName)
                    .storedName(storedName)
                    .contentType(file.getContentType())
                    .fileSize(file.getSize())
                    .filePath(filePath.toString())
                    .build();
            return FileResponse.from(fileEntity);

        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패", e);
        }
    }
}
