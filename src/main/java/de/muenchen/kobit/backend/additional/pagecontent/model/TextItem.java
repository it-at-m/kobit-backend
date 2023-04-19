package de.muenchen.kobit.backend.additional.pagecontent.model;

import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;
import java.net.URL;
import java.util.UUID;
import javax.persistence.*;

@Entity
@Table(name = "text_item")
public class TextItem {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "page_type")
    private PageType pageType;

    @Column(name = "header", columnDefinition = "TEXT")
    private String header;

    @Column(name = "entry", columnDefinition = "TEXT")
    private String entry;

    @Column(name = "link")
    private URL link;

    public UUID getId() { // Add this getter
        return id;
    }

    public void setId(UUID id) { // Add this setter
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

    public void setLink(URL link) {
        this.link = link;
    }

    public TextItem(UUID id, PageType pageType, String header, String entry, URL link) {
        this.id = id;
        this.pageType = pageType;
        this.header = header;
        this.entry = entry;
        this.link = link;
    }

    public TextItem() {
        // Required by JPA
    }

    public TextItemView toView() {
        TextItemView view = new TextItemView();
        view.setId(id); // Add this line
        view.setHeader(header);
        view.setEntry(entry);

        view.setLink(link != null ? link.toString() : null);

        return view;
    }
}
