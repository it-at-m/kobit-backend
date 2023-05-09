package de.muenchen.kobit.backend.additional.pagecontent.model;

import de.muenchen.kobit.backend.additional.pagecontent.view.TextItemView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "text_item")
public class TextItem {

    @Id
    @NotNull
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Enumerated(EnumType.STRING)
    private PageType pageType;

    @Size(min = 3, message = "violation if minimal name length")
    @Column(name = "header", columnDefinition = "TEXT")
    private String header;

    @Size(min = 5, message = "violation if minimal name length")
    @Column(name = "entry", columnDefinition = "TEXT")
    private String entry;

    @Column(name = "link")
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

    public void setLink(URL link) {
        this.link = link;
    }

    public TextItem(PageType pageType, String header, String entry, URL link) {
        this.pageType = pageType;
        this.header = header;
        this.entry = entry;
        this.link = link;
    }

    public TextItem() {

    }

    public TextItemView toView() {
        try {
            TextItemView view = new TextItemView();
            view.setId(id);
            view.setPageType(pageType);
            view.setHeader(header);
            view.setEntry(entry);

            view.setLink(link != null ? link.toString() : null);

            return view;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error while converting TextItem to TextItemView", e);
        }
    }

}
