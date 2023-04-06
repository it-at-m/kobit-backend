package de.muenchen.kobit.backend.contactpoint.view;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.links.view.LinkView;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactPointView {

    UUID id;
    String name;
    String shortCut;
    String description;
    String department;
    List<ContactView> contact;
    List<Competence> competences;
    List<LinkView> links;

    public final ContactPoint toContactPoint() {
        return new ContactPoint(this.name, this.shortCut, this.description, this.department);
    }
}
