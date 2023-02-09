package de.muenchen.kobit.backend.links.model;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Slf4j
public class Link {

    @Id
    @NotNull
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull private UUID contactPointId;

    @NotNull
    @Size(min = 3, message = "violation if minimal name length")
    private String name;

    @NotNull private String url;
    private boolean inDownloads;

    public Link(UUID contactPointId, String name, String url, boolean inDownloads) {
        verifyURL(url);
        this.contactPointId = contactPointId;
        this.name = name;
        this.url = url;
        this.inDownloads = inDownloads;
    }

    public Link(UUID id, UUID contactPointId, String name, String url, boolean inDownloads) {
        verifyURL(url);
        this.id = id;
        this.contactPointId = contactPointId;
        this.name = name;
        this.url = url;
        this.inDownloads = inDownloads;
    }

    private void verifyURL(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            log.error("URL-String is not a valid URL!");
        }
    }
}
