package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.model.ContentItem;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.repository.ContentItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.validation.ContentItemValidator;
import de.muenchen.kobit.backend.validation.exception.ContentItemValidationException;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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
    public ContentItemView updateContentItem(UUID itemId, ContentItemView newContentItem) {

        for (ContentItemValidator validator : validators) {
            try {
                validator.validate(newContentItem);
            } catch (ContentItemValidationException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
            }
        }

        try {
            ContentItem existingContentItem =
                    contentItemRepository
                            .findById(itemId)
                            .orElseThrow(
                                    () ->
                                            new ResponseStatusException(
                                                    HttpStatus.BAD_REQUEST,
                                                    "Content item not found with the given ID."));

            PageType pageType = existingContentItem.getPageType();

            ContentItem updatedContentItem = newContentItem.toContentItem();
            updatedContentItem.setId(itemId);
            updatedContentItem.setPageType(pageType);
            ContentItem savedContentItem = contentItemRepository.save(updatedContentItem);
            ContentItemView savedContentView = ContentItemView.toView(savedContentItem);
            return savedContentView;

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
