package de.muenchen.kobit.backend.contactpoint.view;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactPointListItem {

    UUID id;

    String name;

    String shortCut;

    List<String> department;
}
