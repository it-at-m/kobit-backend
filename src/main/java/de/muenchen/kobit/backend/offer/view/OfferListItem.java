package de.muenchen.kobit.backend.offer.view;

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
public class OfferListItem {

    UUID id;

    Date startDate;

    Date endDate;

    String title;

    String description;

    URL imageLink;
}
