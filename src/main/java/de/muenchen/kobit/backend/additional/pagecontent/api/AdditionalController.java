package de.muenchen.kobit.backend.additional.pagecontent.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.service.ContentItemManipulationService;
import de.muenchen.kobit.backend.additional.pagecontent.service.ItemService;
import de.muenchen.kobit.backend.additional.pagecontent.service.S3UploadService;
import de.muenchen.kobit.backend.additional.pagecontent.service.TextItemCreationService;
import de.muenchen.kobit.backend.additional.pagecontent.service.TextItemDeletionService;
import de.muenchen.kobit.backend.additional.pagecontent.service.TextItemManipulationService;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.additional.pagecontent.view.ItemWrapper;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    AdditionalController(
            ItemService itemService,
            ContentItemManipulationService contentItemManipulationService,
            TextItemManipulationService textItemManipulationService,
            TextItemDeletionService textItemDeletionService,
            TextItemCreationService textItemCreationService,
            S3UploadService s3UploadService) {
        this.itemService = itemService;
        this.contentItemManipulationService = contentItemManipulationService;
        this.textItemManipulationService = textItemManipulationService;
        this.textItemDeletionService = textItemDeletionService;
        this.textItemCreationService = textItemCreationService;
        this.s3UploadService = s3UploadService;
    }

    @GetMapping("/{pageType}")
    ItemWrapper getPageByType(@PathVariable PageType pageType) {
        return itemService.getItemsForPage(pageType);
    }

    @PostMapping("/{pageType}")
    public ResponseEntity<?> createTextItem(
            @PathVariable PageType pageType,
            @RequestBody TextItemView textItemView,
            @RequestPart(value = "file", required = false) MultipartFile file)
            throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(textItemView);
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if (pageType == PageType.GLOSSARY || pageType == PageType.FAQ) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                String json = mapper.writeValueAsString(textItemView);
                System.out.println(json);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(textItemView.getEntry());
            System.out.println(textItemView.getHeader());
            System.out.println(textItemView.getId());
            System.out.println(textItemView.getLink());
            return textItemCreationService.createTextItem(textItemView);
        } else if (pageType == PageType.DOWNLOADS) {
            if (file == null) {
                throw new IllegalArgumentException("File is required for the DOWNLOADS page type.");
            }
            try {
                String fileName = s3UploadService.uploadFile(file);
                // You can now create a TextItem with the link to the uploaded file and save it
                // to the database.
                // ...

                return ResponseEntity.ok().build();
            } catch (IOException e) {
                // Handle the exception, e.g., log the error, return an error response, etc.
                throw e;
            }
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @PutMapping("/{pageType}/text-item/{id}")
    public ResponseEntity<?> updateTextItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestBody TextItemView textItemView,
            @RequestPart(value = "file", required = false) MultipartFile file)
            throws IOException {
        if (pageType == PageType.GLOSSARY || pageType == PageType.FAQ) {
            return textItemManipulationService.updateTextItem(id, textItemView);
        } else if (pageType == PageType.DOWNLOADS) {
            if (file != null) {
                try {
                    String fileName = s3UploadService.uploadFile(file);
                    // You can now update the TextItem with the link to the uploaded file and save
                    // it to the database.
                    // ...
                } catch (IOException e) {
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
    public void deleteTextItem(@PathVariable PageType pageType, @PathVariable UUID id) {
        if (pageType == PageType.GLOSSARY
                || pageType == PageType.DOWNLOADS
                || pageType == PageType.FAQ) {
            textItemDeletionService.deleteTextItemView(id);
        } else {
            throw new UnsupportedOperationException("Operation not supported for this page type.");
        }
    }
}
