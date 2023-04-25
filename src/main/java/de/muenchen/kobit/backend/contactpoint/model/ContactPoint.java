package de.muenchen.kobit.backend.contactpoint.model;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointListItem;

import java.util.List;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ContactPoint {

    @Id
    @NotNull
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Size(min = 5, message = "violation if minimal name length")
    @NotNull
    private String name;

    @Size(min = 3, message = "violation if minimal shortcut length")
    @Size(max = 10, message = "violation if maximal shortcut length")
    @NotNull
    private String shortCut;

    @NotNull private String description;
    /**
     * Departments contains a list of String each entry is a single department - Referat
     * a ContactPoint can be assigned to more than one department
     * if a ContactPoint has no department (the list is empty)
     */
    @ElementCollection
    @CollectionTable(
            name="departments",
            joinColumns=@JoinColumn(name="contact_point_id")
    )
    private List<String> departments;

    public ContactPoint(String name, String shortCut, String description, List<String> departments) {
        this.name = name;
        this.description = description;
        this.shortCut = shortCut;
        this.departments = departments;
    }

    public ContactPointListItem toListView() {
        return new ContactPointListItem(id, name, shortCut);
    }
}
