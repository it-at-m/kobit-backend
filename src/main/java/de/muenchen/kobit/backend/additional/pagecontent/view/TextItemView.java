package de.muenchen.kobit.backend.additional.pagecontent.view;

import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import java.net.URL;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TextItemView {

    private String header;

    private String entry;

    private URL link;

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

    public void setLink(URL link) {
        this.link = link;
    }

    public TextItem toTextItem(PageType pageType) {
        return new TextItem(pageType, header, entry, link);
    }
}
