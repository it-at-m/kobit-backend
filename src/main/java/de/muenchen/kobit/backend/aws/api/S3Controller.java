package de.muenchen.kobit.backend.aws.api;

import de.muenchen.kobit.backend.aws.service.S3DeletionService;
import de.muenchen.kobit.backend.aws.service.S3ManipulationService;
import de.muenchen.kobit.backend.aws.service.S3UploadService;
import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/s3")
public class S3Controller {
    private final S3UploadService s3UploadService;
    private final S3DeletionService s3DeletionService;
    private final S3ManipulationService s3ManipulationService;

    S3Controller(
            S3UploadService s3UploadService,
            S3DeletionService s3DeletionService,
            S3ManipulationService s3ManipulationService) {
        this.s3UploadService = s3UploadService;
        this.s3DeletionService = s3DeletionService;
        this.s3ManipulationService = s3ManipulationService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file)
            throws IOException, S3FileValidationException, NoSuchAlgorithmException {

        String link = s3UploadService.uploadFile(file);
        return ResponseEntity.ok(link);
    }

    @DeleteMapping("/delete")
    public void deleteFile(@RequestParam(value = "link", required = false) String link) {
        s3DeletionService.deleteFileByLink(link);
    }
}
