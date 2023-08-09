package de.muenchen.kobit.backend.contactpoint.view;

import de.muenchen.kobit.backend.competence.Competence;
import java.util.List;
import javax.validation.constraints.NotNull;

public class ListItemToCompetenceView {
    private ContactPointListItem listItem;

    private List<Competence> competences;

    private int position;

    public ListItemToCompetenceView(
            @NotNull ContactPointListItem listItem, List<Competence> competences, int position) {
        assert (listItem.id != null);
        this.listItem = listItem;
        this.competences = competences;
        this.position = position;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
