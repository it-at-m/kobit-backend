package de.muenchen.kobit.backend.additional.pagecontent.view;
import java.net.MalformedURLException;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import de.muenchen.kobit.backend.additional.pagecontent.model.PageType;
import de.muenchen.kobit.backend.additional.pagecontent.model.TextItem;
import java.net.URL;

@AllArgsConstructor
@NoArgsConstructor
public class TextItemView {

    private UUID uuid;

    private String header;

    private String entry;

    private URL link;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    public TextItem toTextItem(PageType pageType) throws MalformedURLException {
        return new TextItem(uuid, pageType, header, entry, link);
    }

    public static TextItemView toView(TextItem textItem) {
        TextItemView view = new TextItemView();
        view.setHeader(textItem.getHeader());
        view.setEntry(textItem.getEntry());
        view.setLink(textItem.getLink() != null ? textItem.getLink().toString() : null);

        return view;
    }
}
