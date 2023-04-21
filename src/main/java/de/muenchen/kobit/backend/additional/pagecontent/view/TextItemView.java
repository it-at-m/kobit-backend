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

    private UUID id; // Replace 'uuid' with 'id'

    private PageType pageType;

    private String header;

    private String entry;

    private URL link;

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

    // modified to accept a String and convert it to a URL
    public void setLink(String link) {
        try {
            this.link = link != null ? new URL(link) : null;
        } catch (MalformedURLException e) {
            // Handle the exception (e.g., log the error and set the link to null)
            this.link = null;
        }
    }

    public TextItem toTextItem() throws MalformedURLException {
        return new TextItem(id, pageType, header, entry, link); // Replace 'uuid' with 'id'
    }

    public static TextItemView toView(TextItem textItem) {
        TextItemView view = new TextItemView();
        view.setId(textItem.getId()); // Replace 'setUuid' with 'setId'
        view.setPageType(textItem.getPageType());
        view.setHeader(textItem.getHeader());
        view.setEntry(textItem.getEntry());
        view.setLink(textItem.getLink() != null ? textItem.getLink().toString() : null);

        return view;
    }
}
