package de.muenchen.kobit.backend.additional.pagecontent.view;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TextItemView {

    private UUID id;

    private PageType pageType;

    private String header;

    private String entry;

    private URL link;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public PageType getPageType() {
        return pageType;
    }

    public void setPageType(PageType pageType) {
        this.pageType = pageType;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(String link) throws MalformedURLException {
        if (link != null) {
            this.link = new URL(link);
        } else {
            this.link = null;
        }
    }

    public TextItem toTextItem() throws MalformedURLException {
        return new TextItem(this.pageType, this.header, this.entry, this.link);
    }

    public static TextItemView toView(TextItem textItem) throws MalformedURLException {
        TextItemView view = new TextItemView();
        view.setId(textItem.getId());
        view.setPageType(textItem.getPageType());
        view.setHeader(textItem.getHeader());
        view.setEntry(textItem.getEntry());
        view.setLink(textItem.getLink() != null ? textItem.getLink().toString() : null);

        return view;
    }
}
