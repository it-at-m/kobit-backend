package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.model.ContentItem;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.repository.ContentItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.validation.ContentItemValidator;
import de.muenchen.kobit.backend.validation.exception.ContentItemValidationException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContentItemManipulationService {

    private final ContentItemRepository contentItemRepository;

    private final List<ContentItemValidator<ContentItemView>> validators;

    ContentItemManipulationService(
            ContentItemRepository contentItemRepository,
            List<ContentItemValidator<ContentItemView>> validators) {
        this.contentItemRepository = contentItemRepository;
        this.validators = validators;
    }

    @Transactional
    public ResponseEntity<?> updateContentItem(UUID itemId, ContentItemView newContentItem) {

        // Validate newContentItem is not null and content is not blank
        if (newContentItem == null) {
            return ResponseEntity.badRequest().body("ContentItem cannot be null.");
        }

        for (ContentItemValidator validator : validators) {
            try {
                validator.validate(newContentItem);
            } catch (ContentItemValidationException e) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", e.getMessage()));
            }
        }

        try {
            ContentItem existingContentItem =
                    contentItemRepository
                            .findById(itemId)
                            .orElseThrow(
                                    () ->
                                            new IllegalArgumentException(
                                                    "ContentItem not found with the given ID."));

            PageType pageType = existingContentItem.getPageType();
            if (!isTextPage(pageType)) {
                ContentItem updatedContentItem = newContentItem.toContentItem();
                updatedContentItem.setId(itemId);
                updatedContentItem.setPageType(pageType);
                ContentItem savedContentItem = contentItemRepository.save(updatedContentItem);
                ContentItemView savedContentView = ContentItemView.toView(savedContentItem);
                return ResponseEntity.ok(savedContentView);
            }
            throw new UnsupportedOperationException("Operation not supported for this page type.");
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    private static boolean isTextPage(PageType pageType) {
        return pageType == PageType.FAQ
                || pageType == PageType.GLOSSARY
                || pageType == PageType.DOWNLOADS
                || pageType == PageType.PRIVACY_POLICY;
    }
}
