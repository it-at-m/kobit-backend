package de.muenchen.kobit.backend.additional.serviceagreement.model;

import de.muenchen.kobit.backend.additional.serviceagreement.view.PossibleSolutionView;
import de.muenchen.kobit.backend.additional.serviceagreement.view.TabView;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tab {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private UUID stepId;

    private String header;

    public Tab(UUID stepId, String header) {
        this.stepId = stepId;
        this.header = header;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStepId() {
        return stepId;
    }

    public void setStepId(UUID stepId) {
        this.stepId = stepId;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public TabView toView(List<PossibleSolutionView> possibleSolutionViews) {
        return new TabView(header, possibleSolutionViews);
    }
}
