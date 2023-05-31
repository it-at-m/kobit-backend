package de.muenchen.kobit.backend.offer.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import de.muenchen.kobit.backend.offer.model.Offer;
import java.net.URL;
import java.sql.Date;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    Date endDate;

    String title;

    String description;

    URL imageLink;

    public final Offer toOffer() {
        return new Offer(
                this.startDate, this.endDate, this.title, this.description, this.imageLink);
    }
}
