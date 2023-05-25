package de.muenchen.kobit.backend.offer.view;

import de.muenchen.kobit.backend.offer.model.Offer;
import java.net.URL;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferView {

    UUID id;

    String startDate;

    String endDate;

    String title;

    String description;

    URL imageLink;

    public final Offer toOffer() {
        return new Offer(
                this.startDate, this.endDate, this.title, this.description, this.imageLink);
    }
}
