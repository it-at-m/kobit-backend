package de.muenchen.kobit.backend.contact.model;

import de.muenchen.kobit.backend.contact.view.ContactView;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(ContactId.class)
public class Contact {

    @Id @NotNull private UUID contactPointId;

    @Id @NotNull @NotBlank private String email;

    public ContactView toView() {
        return new ContactView(this.getEmail());
    }
}
