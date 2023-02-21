package de.muenchen.kobit.backend.downloads.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Download {
    @Id private UUID id;

    private String url;

    private String name;

    public Download(String url, String name) {
        this.url = url;
        this.name = name;
    }
}
