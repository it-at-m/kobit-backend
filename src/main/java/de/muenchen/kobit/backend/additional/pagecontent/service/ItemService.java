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
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    private final TextItemRepository textItemRepository;
    private final ContentItemRepository contentItemRepository;

    public ItemService(
            TextItemRepository textItemRepository, ContentItemRepository contentItemRepository) {
        this.textItemRepository = textItemRepository;
        this.contentItemRepository = contentItemRepository;
    }

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

    private List<TextItemView> getAllTextsForPage(PageType pageType) {
        return textItemRepository.findAllByPageType(pageType).stream()
                .map(TextItem::toView)
                .collect(Collectors.toList());
    }

    private List<ContentItemView> getAllContentForPage(PageType pageType) {
        return contentItemRepository.findAllByPageType(pageType).stream()
                .map(ContentItem::toView)
                .collect(Collectors.toList());
    }

    private static boolean isTextPage(PageType pageType) {
        return pageType == PageType.FAQ
                || pageType == PageType.GLOSSARY
                || pageType == PageType.DOWNLOADS
                || pageType == PageType.PRIVACY_POLICY;
    }
}
