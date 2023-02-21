package de.muenchen.kobit.backend.additional.pagecontent.api;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.service.ItemService;
import de.muenchen.kobit.backend.additional.pagecontent.view.ItemWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
