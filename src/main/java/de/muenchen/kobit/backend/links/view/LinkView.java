package de.muenchen.kobit.backend.links.view;

import de.muenchen.kobit.backend.links.model.Link;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkView {

    private UUID contactPointId;
    private String name;
    private String url;

    private boolean inDownloads;

    public final Link toLink() {
        return new Link(this.contactPointId, this.name, this.url, this.inDownloads);
    }

    public final Link toLink(UUID contactPointId) {
        return new Link(contactPointId, this.name, this.url, this.inDownloads);
    }
}
