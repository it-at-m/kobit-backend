package de.muenchen.kobit.backend.additional.pagecontent.api;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.service.ItemService;
import de.muenchen.kobit.backend.additional.pagecontent.view.ItemWrapper;
import java.util.Collections;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/additional")
public class AdditionalController {

    private final ItemService itemService;

    AdditionalController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{pageType}")
    ItemWrapper getPageByType(@PathVariable PageType pageType) {
        return itemService.getItemsForPage(pageType);
    }

    @PostMapping("/{pageType}")
    @PreAuthorize("hasAuthority('KOBIT_ADMIN')")
    public ItemWrapper createTextItem(
            @PathVariable PageType pageType, @RequestBody TextItem textItem) {
        if (pageType == PageType.GLOSSARY || pageType == PageType.DOWNLOADS) {
            TextItem createdTextItem = itemService.createTextItem(pageType, textItem);
            ItemWrapper itemWrapper =
                    new ItemWrapper.ItemWrapperBuilder()
                            .textItems(Collections.singletonList(createdTextItem.toView()))
                            .build();
            return itemWrapper;
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @PutMapping("/{pageType}/{itemId}")
    @PreAuthorize("hasAuthority('KOBIT_ADMIN')")
    public ItemWrapper updateTextItem(
            @PathVariable PageType pageType,
            @PathVariable UUID itemId,
            @RequestBody TextItem textItem) {
        if (pageType == PageType.GLOSSARY || pageType == PageType.DOWNLOADS) {
            TextItem updatedTextItem = itemService.updateTextItem(itemId, textItem);
            ItemWrapper itemWrapper =
                    new ItemWrapper.ItemWrapperBuilder()
                            .textItems(Collections.singletonList(updatedTextItem.toView()))
                            .build();
            return itemWrapper;
        }
        throw new UnsupportedOperationException("Operation not supported for this page type.");
    }

    @DeleteMapping("/{pageType}/{itemId}")
    @PreAuthorize("hasAuthority('KOBIT_ADMIN')")
    public void deleteTextItem(@PathVariable PageType pageType, @PathVariable UUID itemId) {
        if (pageType == PageType.GLOSSARY || pageType == PageType.DOWNLOADS) {
            itemService.deleteTextItem(itemId);
        } else {
            throw new UnsupportedOperationException("Operation not supported for this page type.");
        }
    }
}
