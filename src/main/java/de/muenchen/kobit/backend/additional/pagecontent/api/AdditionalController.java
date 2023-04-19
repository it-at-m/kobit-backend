package de.muenchen.kobit.backend.additional.pagecontent.api;

import de.muenchen.kobit.backend.additional.pagecontent.model.ContentItem;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.service.ItemService;
import de.muenchen.kobit.backend.additional.pagecontent.view.ItemWrapper;
import java.util.Collections;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.security.access.prepost.PreAuthorize;

@Slf4j
@RestController
@RequestMapping("/additional")
public class AdditionalController {

    private final ItemService itemService;

    AdditionalController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{pageType}")
    ItemWrapper getPageByType(@PathVariable PageType pageType) {
        if (pageType == PageType.PREVENTION) {
            System.out.println("Prevention");
        }
        return itemService.getItemsForPage(pageType);
    }

    @PostMapping("/{pageType}")
    public ItemWrapper createTextItem(
            @PathVariable PageType pageType, @RequestBody TextItem textItem) {
        if (pageType == PageType.GLOSSARY
                || pageType == PageType.DOWNLOADS
                || pageType == PageType.FAQ) {
            TextItem createdTextItem = itemService.createTextItem(pageType, textItem);
            ItemWrapper itemWrapper =
                    new ItemWrapper.ItemWrapperBuilder()
                            .textItems(Collections.singletonList(createdTextItem.toView()))
                            .build();
            return itemWrapper;
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @PutMapping("/{pageType}/text-item/{id}")
    public ItemWrapper updateTextItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestBody TextItem textItem) {
        if (pageType == PageType.GLOSSARY
                || pageType == PageType.DOWNLOADS
                || pageType == PageType.FAQ) {
            TextItem updatedTextItem = itemService.updateTextItem(id, textItem);
            ItemWrapper itemWrapper =
                    new ItemWrapper.ItemWrapperBuilder()
                            .textItems(Collections.singletonList(updatedTextItem.toView()))
                            .build();
            return itemWrapper;
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @PutMapping("/{pageType}/content-item/{id}")
    public ItemWrapper updateContentItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestBody ContentItem contentItem) {
        if (pageType == PageType.PREVENTION || pageType == PageType.LEADERSHIP) {
            ContentItem updatedContentItem = itemService.updateContentItem(id, contentItem);
            ItemWrapper itemWrapper =
                    new ItemWrapper.ItemWrapperBuilder()
                            .contentItems(Collections.singletonList(updatedContentItem.toView()))
                            .build();
            return itemWrapper;
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @DeleteMapping("/{pageType}/{id}")
    public void deleteTextItem(@PathVariable PageType pageType, @PathVariable UUID id) {
        if (pageType == PageType.GLOSSARY || pageType == PageType.DOWNLOADS) {
            itemService.deleteTextItem(id);
        } else {
            throw new UnsupportedOperationException("Operation not supported for this page type.");
        }
    }
}
