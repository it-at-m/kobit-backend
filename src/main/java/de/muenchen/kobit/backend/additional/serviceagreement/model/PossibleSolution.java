package de.muenchen.kobit.backend.additional.serviceagreement.model;

import de.muenchen.kobit.backend.additional.serviceagreement.view.PossibleSolutionView;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class PossibleSolution {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull private String header;

    private String text;

    @NotNull private UUID tabId;

    public PossibleSolution(String header, String text, UUID tabId) {
        this.header = header;
        this.text = text;
        this.tabId = tabId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getTabId() {
        return tabId;
    }

    public void setTabId(UUID tabId) {
        this.tabId = tabId;
    }

    public PossibleSolutionView toView() {
        return new PossibleSolutionView(header, text);
    }
}
