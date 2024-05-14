package de.muenchen.kobit.backend.additional.pagecontent.api;

import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.service.*;
import de.muenchen.kobit.backend.additional.pagecontent.view.ContentItemView;
import de.muenchen.kobit.backend.additional.pagecontent.view.ItemWrapper;
import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import de.muenchen.kobit.backend.validation.exception.S3FileValidationException;
import java.io.IOException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/additional")
public class AdditionalController {

    private final ItemService itemService;
    private final ContentItemManipulationService contentItemManipulationService;
    private final TextItemManipulationService textItemManipulationService;
    private final TextItemDeletionService textItemDeletionService;
    private final TextItemCreationService textItemCreationService;
    private final AdminService adminService;

    AdditionalController(
            ItemService itemService,
            ContentItemManipulationService contentItemManipulationService,
            TextItemManipulationService textItemManipulationService,
            TextItemDeletionService textItemDeletionService,
            TextItemCreationService textItemCreationService,
            AdminService adminService) {
        this.itemService = itemService;
        this.contentItemManipulationService = contentItemManipulationService;
        this.textItemManipulationService = textItemManipulationService;
        this.textItemDeletionService = textItemDeletionService;
        this.textItemCreationService = textItemCreationService;
        this.adminService = adminService;
    }

    @PostMapping(value = "/{pageType}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("@adminService.isUserKobitAdmin()")
    public TextItemView createTextItem(
            @PathVariable PageType pageType, @RequestBody TextItemView textItemView)
            throws IOException, S3FileValidationException {

        TextItemView response = textItemCreationService.createTextItem(textItemView);
        return response;
    }

    @GetMapping("/{pageType}")
    public ItemWrapper getPageByType(@PathVariable PageType pageType) {
        return itemService.getItemsForPage(pageType);
    }

    @PutMapping("/{pageType}/text-item/{id}")
    @PreAuthorize("@adminService.isUserKobitAdmin()")
    public TextItemView updateTextItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestBody TextItemView textItemView) {

        TextItemView response = textItemManipulationService.updateTextItem(id, textItemView);
        return response;
    }

    @PutMapping("/{pageType}/content-item/{id}")
    @PreAuthorize("@adminService.isUserKobitAdmin()")
    public ContentItemView updateContentItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestBody ContentItemView contentItem) {

        ContentItemView response =
                contentItemManipulationService.updateContentItem(id, contentItem);
        return response;
    }

    @DeleteMapping("/{pageType}/text-item/{id}")
    @PreAuthorize("@adminService.isUserKobitAdmin()")
    public void deleteTextItem(
            @PathVariable PageType pageType,
            @PathVariable UUID id,
            @RequestParam(value = "link", required = false) String link) {

        textItemDeletionService.deleteTextItemView(id);
    }
}
