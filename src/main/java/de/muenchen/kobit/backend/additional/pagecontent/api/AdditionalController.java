package de.muenchen.kobit.backend.additional.pagecontent.api;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.service.*;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.additional.pagecontent.view.ItemWrapper;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/additional")
public class AdditionalController {

    private final ItemService itemService;
    private final ContentItemManipulationService contentItemManipulationService;
    private final TextItemManipulationService textItemManipulationService;
    private final TextItemDeletionService textItemDeletionService;
    private final TextItemCreationService textItemCreationService;
    private final S3UploadService s3UploadService;
    private final S3DeletionService s3DeletionService;
    private final S3ManipulationService s3ManipulationService;

    AdditionalController(
            ItemService itemService,
            ContentItemManipulationService contentItemManipulationService,
            TextItemManipulationService textItemManipulationService,
            TextItemDeletionService textItemDeletionService,
            TextItemCreationService textItemCreationService,
            S3UploadService s3UploadService,
            S3DeletionService s3DeletionService,
            S3ManipulationService s3ManipulationService) {
        this.itemService = itemService;
        this.contentItemManipulationService = contentItemManipulationService;
        this.textItemManipulationService = textItemManipulationService;
        this.textItemDeletionService = textItemDeletionService;
        this.textItemCreationService = textItemCreationService;
        this.s3UploadService = s3UploadService;
        this.s3DeletionService = s3DeletionService;
        this.s3ManipulationService = s3ManipulationService;
    }

    @PostMapping(value = "/{pageType}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTextItem(
            @PathVariable PageType pageType, @RequestBody TextItemView textItemView)
            throws IOException, S3FileValidationException {

        ResponseEntity<?> response = textItemCreationService.createTextItem(textItemView);

        if (pageType == PageType.DOWNLOADS && textItemView.getLink() == null) {
            throw new IllegalStateException(
                    "Link cannot be null in created text item for downloads");
        } else {
            return response;
        }

        /*
        if (response.getStatusCode() == HttpStatus.CREATED) {
            // Get the link from the response
            TextItemView createdTextItem = (TextItemView) response.getBody();
            if (createdTextItem != null && createdTextItem.getLink() != null) {
                String link = createdTextItem.getLink().toString();
                System.out.println(link);
                // Do something with the link
            } else {
                // Handle the case where the link is null
                throw new IllegalStateException("Link is null in created text item");
            }
        }*/

    }

    @PostMapping(value = "/file/{pageType}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(
            @PathVariable PageType pageType, @RequestParam("file") MultipartFile file)
            throws IOException, S3FileValidationException, NoSuchAlgorithmException {

        String link = s3UploadService.uploadFile(file);

        return ResponseEntity.ok(link);
    }

    @GetMapping("/{pageType}")
    ItemWrapper getPageByType(@PathVariable PageType pageType) {
        return itemService.getItemsForPage(pageType);
    }

    @PutMapping("/{pageType}/text-item/{id}")
    public ResponseEntity<?> updateTextItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestBody TextItemView textItemView,
            @RequestPart(value = "file", required = false) MultipartFile newFile)
            throws IOException {
        if (pageType == PageType.GLOSSARY || pageType == PageType.FAQ) {
            return textItemManipulationService.updateTextItem(id, textItemView);
        } else if (pageType == PageType.DOWNLOADS) {
            if (newFile != null) {
                try {
                    String newLink =
                            s3ManipulationService.replaceFile(
                                    textItemView.getLink().toString(), newFile);

                    textItemView.setLink(newLink);

                    return textItemManipulationService.updateTextItem(id, textItemView);
                    // You can now update the TextItem with the link to the uploaded file and save
                    // it to the database.
                    // ...
                } catch (IOException | S3FileValidationException e) {
                    // Handle the exception, e.g., log the error, return an error response, etc.
                }
            }
            return textItemManipulationService.updateTextItem(id, textItemView);
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @PutMapping("/{pageType}/content-item/{id}")
    public ResponseEntity<?> updateContentItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestBody ContentItemView contentItem) {
        if (pageType == PageType.PREVENTION || pageType == PageType.LEADERSHIP) {
            return contentItemManipulationService.updateContentItem(id, contentItem);
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @DeleteMapping("/{pageType}/text-item/{id}")
    public void deleteTextItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestParam(value = "link", required = false) String link) {
        if (pageType == PageType.GLOSSARY || pageType == PageType.FAQ) {
            textItemDeletionService.deleteTextItemView(id);
        } else if (pageType == PageType.DOWNLOADS && link != null) {
            // Get the link to the file from the database, then delete it from S3
            s3DeletionService.deleteFileByLink(link);
            textItemDeletionService.deleteTextItemView(id);
        } else {
            throw new UnsupportedOperationException("Operation not supported for this page type.");
        }
    }
}
