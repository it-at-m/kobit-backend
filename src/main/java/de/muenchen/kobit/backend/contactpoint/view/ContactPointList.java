package de.muenchen.kobit.backend.contactpoint.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactPointList {

    UUID id;

    String name;

    String shortCut;
}
