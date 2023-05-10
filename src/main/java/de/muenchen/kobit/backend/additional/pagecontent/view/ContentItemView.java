package de.muenchen.kobit.backend.additional.pagecontent.view;

import de.muenchen.kobit.backend.additional.pagecontent.model.ContentItem;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ContentItemView {

    private UUID id;

    private PageType pageType;

    private String content;

    public UUID getId() { // Replace 'getUuid' with 'getId'
        return id;
    }

    public void setId(UUID id) { // Replace 'setUuid' with 'setId'
        this.id = id;
    }

    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ContentItem toContentItem() {
        return new ContentItem(pageType, content);
    }

    public static ContentItemView toView(ContentItem contentItem) {
        ContentItemView view = new ContentItemView();
        view.setId(contentItem.getId());
        view.setPageType(contentItem.getPageType());
        view.setContent(contentItem.getContent());

        return view;
    }
}
