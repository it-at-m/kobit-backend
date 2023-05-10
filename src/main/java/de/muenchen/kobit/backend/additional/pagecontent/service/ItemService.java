package de.muenchen.kobit.backend.additional.pagecontent.service;

import de.muenchen.kobit.backend.additional.pagecontent.model.ContentItem;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.repository.ContentItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.repository.TextItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.additional.pagecontent.view.ItemWrapper;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ItemService {

    private final TextItemRepository textItemRepository;
    private final ContentItemRepository contentItemRepository;

    public ItemService(
            TextItemRepository textItemRepository, ContentItemRepository contentItemRepository) {
        this.textItemRepository = textItemRepository;
        this.contentItemRepository = contentItemRepository;
    }

    @Transactional(readOnly = true)
    public ItemWrapper getItemsForPage(PageType pageType) {
        ItemWrapper wrapper;
        if (isTextPage(pageType)) {
            wrapper =
                    new ItemWrapper.ItemWrapperBuilder()
                            .textItems(getAllTextsForPage(pageType))
                            .build();
        } else {
            wrapper =
                    new ItemWrapper.ItemWrapperBuilder()
                            .contentItems(getAllContentForPage(pageType))
                            .build();
        }
        return wrapper;
    }

    @Transactional(readOnly = true)
    private List<TextItemView> getAllTextsForPage(PageType pageType) {
        try {
            return textItemRepository.findAllByPageType(pageType).stream()
                    .map(TextItem::toView)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all texts for page " + pageType, e);
        }
    }

    @Transactional(readOnly = true)
    private List<ContentItemView> getAllContentForPage(PageType pageType) {
        return contentItemRepository.findAllByPageType(pageType).stream()
                .map(ContentItem::toView)
                .collect(Collectors.toList());
    }

    @Transactional
    public TextItem createTextItem(PageType pageType, TextItem textItem) {
        if (isTextPage(pageType)) {
            textItem.setPageType(pageType);
            return textItemRepository.save(textItem);
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Operation not supported for this page type.");
    }

    @Transactional
    public TextItem updateTextItem(UUID itemId, TextItem newTextItem) {
        TextItem existingTextItem =
                textItemRepository
                        .findById(itemId)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "TextItem not found with the given ID."));

        PageType pageType = existingTextItem.getPageType();
        if (isTextPage(pageType)) {
            newTextItem.setId(itemId);
            newTextItem.setPageType(pageType);
            return textItemRepository.save(newTextItem);
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Operation not supported for this page type.");
    }

    @Transactional
    public ContentItem updateContentItem(UUID itemId, ContentItem newContentItem) {
        ContentItem existingContentItem =
                contentItemRepository
                        .findById(itemId)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "ContentItem not found with the given ID."));

        PageType pageType = existingContentItem.getPageType();
        if (!isTextPage(pageType)) {
            newContentItem.setId(itemId);
            newContentItem.setPageType(pageType);
            return contentItemRepository.save(newContentItem);
        }
        throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, "Operation not supported for this page type.");
    }

    @Transactional
    public void deleteTextItem(UUID itemId) {
        TextItem textItem =
                textItemRepository
                        .findById(itemId)
                        .orElseThrow(
                                () ->
                                        new IllegalArgumentException(
                                                "TextItem not found with the given ID."));

        PageType pageType = textItem.getPageType();
        if (isTextPage(pageType)) {
            textItemRepository.delete(textItem);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Operation not supported for this page type.");
        }
    }

    private static boolean isTextPage(PageType pageType) {
        return pageType == PageType.FAQ
                || pageType == PageType.GLOSSARY
                || pageType == PageType.DOWNLOADS
                || pageType == PageType.PRIVACY_POLICY;
    }
}
