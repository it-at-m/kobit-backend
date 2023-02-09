package de.muenchen.kobit.backend.contact.view;

import de.muenchen.kobit.backend.contact.model.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ChangeContactWrapper {
    private Contact oldContact;
    private Contact newContact;
}
