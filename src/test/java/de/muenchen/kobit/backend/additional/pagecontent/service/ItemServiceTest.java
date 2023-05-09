package de.muenchen.kobit.backend.additional.pagecontent.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.kobit.backend.additional.pagecontent.model.ContentItem;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import de.muenchen.kobit.backend.additional.pagecontent.repository.ContentItemRepository;
import de.muenchen.kobit.backend.additional.pagecontent.repository.TextItemRepository;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ItemServiceTest {

    private final TextItemRepository textItemRepository = mock(TextItemRepository.class);
    private final ContentItemRepository contentItemRepository = mock(ContentItemRepository.class);

    private ItemService itemService;

    @BeforeEach
    void init() {
        // clearAllCaches();
        itemService = new ItemService(textItemRepository, contentItemRepository);
    }

    @Test
    void getItemsForPageTest_Content() {
        PageType pageType = PageType.NAVIGATOR;
        ContentItem contentItem = new ContentItem(UUID.randomUUID(), PageType.DOWNLOADS, "content");
        when(contentItemRepository.findAllByPageType(pageType)).thenReturn(List.of(contentItem));
        var result = itemService.getItemsForPage(pageType);
        verify(textItemRepository, never()).findAllByPageType(any());
        assertThat(result.getContentItemView()).hasSize(1);
        assertThat(result.getContentItemView().get(0).getContent())
                .isEqualTo(contentItem.getContent());
        assertThat(result.getTextItemView()).isEqualTo(null);
    }

    @Test
    void getItemsForPageTest_Text() throws MalformedURLException {
        PageType pageType = PageType.DOWNLOADS;
        TextItem textItem =
                new TextItem(
                        PageType.DOWNLOADS, "header", "entry", new URL("http://localhost:8080"));
        when(textItemRepository.findAllByPageType(pageType)).thenReturn(List.of(textItem));
        var result = itemService.getItemsForPage(pageType);
        verify(contentItemRepository, never()).findAllByPageType(any());
        assertThat(result.getTextItemView()).hasSize(1);
        assertThat(result.getTextItemView().get(0).getEntry()).isEqualTo(textItem.getEntry());
        assertThat(result.getContentItemView()).isEqualTo(null);
    }
}
