package de.muenchen.kobit.backend.additional.pagecontent.api;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.service.ContentItemManipulationService;
import de.muenchen.kobit.backend.additional.pagecontent.service.ItemService;
import de.muenchen.kobit.backend.additional.pagecontent.service.TextItemCreationService;
import de.muenchen.kobit.backend.additional.pagecontent.service.TextItemDeletionService;
import de.muenchen.kobit.backend.additional.pagecontent.service.TextItemManipulationService;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.additional.pagecontent.view.ItemWrapper;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/additional")
public class AdditionalController {

    private final ItemService itemService;
    private final ContentItemManipulationService contentItemManipulationService;
    private final TextItemManipulationService textItemManipulationService;
    private final TextItemDeletionService textItemDeletionService;
    private final TextItemCreationService textItemCreationService;

    AdditionalController(
            ItemService itemService,
            ContentItemManipulationService contentItemManipulationService,
            TextItemManipulationService textItemManipulationService,
            TextItemDeletionService textItemDeletionService,
            TextItemCreationService textItemCreationService) {
        this.itemService = itemService;
        this.contentItemManipulationService = contentItemManipulationService;
        this.textItemManipulationService = textItemManipulationService;
        this.textItemDeletionService = textItemDeletionService;
        this.textItemCreationService = textItemCreationService;
    }

    @GetMapping("/{pageType}")
    ItemWrapper getPageByType(@PathVariable PageType pageType) {
        return itemService.getItemsForPage(pageType);
    }

    @PostMapping("/{pageType}")
    public ResponseEntity<?> createTextItem(
            @PathVariable PageType pageType, @RequestBody TextItemView textItemView) {
        if (pageType == PageType.GLOSSARY
                || pageType == PageType.DOWNLOADS
                || pageType == PageType.FAQ) {
            return textItemCreationService.createTextItem(textItemView);
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @PutMapping("/{pageType}/text-item/{id}")
    public ResponseEntity<?> updateTextItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestBody TextItemView textItemView) {
        if (pageType == PageType.GLOSSARY
                || pageType == PageType.DOWNLOADS
                || pageType == PageType.FAQ) {
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
        if (pageType == PageType.GLOSSARY || pageType == PageType.DOWNLOADS) {
            textItemDeletionService.deleteTextItemView(id);
        } else {
            throw new UnsupportedOperationException("Operation not supported for this page type.");
        }
    }
}
