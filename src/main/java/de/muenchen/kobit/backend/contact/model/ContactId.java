package de.muenchen.kobit.backend.contact.model;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ContactId implements Serializable {
    private UUID contactPointId;
    private String email;
}
