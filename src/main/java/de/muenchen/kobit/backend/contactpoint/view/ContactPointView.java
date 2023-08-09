package de.muenchen.kobit.backend.contactpoint.view;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.contact.view.ContactView;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.links.view.LinkView;
import java.net.URL;
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
public class ContactPointView {

    private UUID id;
    private String name;
    private String shortCut;
    private String description;
    private List<String> departments;
    private List<ContactView> contact;
    private List<Competence> competences;
    private List<LinkView> links;
    private URL image;
    private Integer position;

    public final ContactPoint toContactPoint() {
        return new ContactPoint(
                this.name, this.shortCut, this.description, this.departments, this.image);
    }

    public ContactPointView(
            UUID id,
            String name,
            String shortCut,
            String description,
            List<String> departments,
            List<ContactView> contact,
            List<Competence> competences,
            List<LinkView> links,
            URL image) {
        this.id = id;
        this.name = name;
        this.shortCut = shortCut;
        this.description = description;
        this.departments = departments;
        this.contact = contact;
        this.competences = competences;
        this.links = links;
        this.image = image;
    }
}
