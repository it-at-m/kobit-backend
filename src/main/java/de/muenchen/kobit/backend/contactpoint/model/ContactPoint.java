package de.muenchen.kobit.backend.contactpoint.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
    private String department;

    public ContactPoint(String name, String shortCut, String description, String department) {
        this.name = name;
        this.description = description;
        this.shortCut = shortCut;
        this.department = department;
    }
}
