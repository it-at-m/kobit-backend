package de.muenchen.kobit.backend.contactpoint.view;

import de.muenchen.kobit.backend.competence.Competence;
import java.util.List;
import javax.validation.constraints.NotNull;

public class ListItemToCompetenceView {
    private ContactPointListItem listItem;

    private List<Competence> competences;

    public ListItemToCompetenceView(
            @NotNull ContactPointListItem listItem, List<Competence> competences) {
        assert (listItem.id != null);
        this.listItem = listItem;
        this.competences = competences;
    }

    public ListItemToCompetenceView() {}

    public ContactPointListItem getListItem() {
        return listItem;
    }

    public void setListItem(@NotNull ContactPointListItem listItem) {
        assert (listItem.id != null);
        this.listItem = listItem;
    }

    public List<Competence> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }
}
