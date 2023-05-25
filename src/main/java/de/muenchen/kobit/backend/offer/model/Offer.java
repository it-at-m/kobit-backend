package de.muenchen.kobit.backend.offer.model;

import de.muenchen.kobit.backend.offer.view.OfferListItem;
import de.muenchen.kobit.backend.offer.view.OfferView;
import java.net.URL;
import java.sql.Date;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offer")
public class Offer {

    @Id
    @NotNull
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Column(name = "start_date")
    private Date startDate;

    @NotNull
    @Column(name = "end_date")
    private Date endDate;

    @NotNull
    @Column(length = 255)
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(name = "image_link", length = 2048)
    private URL imageLink;

    public Offer(Date startDate, Date endDate, String title, String description, URL imageLink) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
        this.imageLink = imageLink;
    }

    public OfferListItem toListView() {
        return new OfferListItem(id, startDate, endDate, title, description, imageLink);
    }

    public OfferView toView() {
        return new OfferView(id, startDate, endDate, title, description, imageLink);
    }
}
