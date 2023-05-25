package de.muenchen.kobit.backend.offer.view;

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
public class OfferListItem {

    UUID id;

    String startDate;

    String endDate;

    String title;

    String description;

    URL imageLink;
}
