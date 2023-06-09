package de.muenchen.kobit.backend.decisiontree.relevance.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Path {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToMany
    @JoinTable(
            name = "competence_to_path",
            joinColumns = @JoinColumn(name = "path_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "competence_id", referencedColumnName = "id"))
    private Set<RelevanceCompetence> competences;

    public Path(Set<RelevanceCompetence> competences) {
        this.competences = competences;
    }
}
