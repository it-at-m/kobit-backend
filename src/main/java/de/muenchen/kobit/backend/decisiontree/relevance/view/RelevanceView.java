package de.muenchen.kobit.backend.decisiontree.relevance.view;

import de.muenchen.kobit.backend.competence.Competence;
import java.util.List;
import java.util.Set;

public class RelevanceView {

    private Set<Competence> path;

    private List<RelevanceOrder> entries;

    public RelevanceView(Set<Competence> path, List<RelevanceOrder> entries) {
        this.path = path;
        this.entries = entries;
    }

    public Set<Competence> getPath() {
        return path;
    }

    public void setPath(Set<Competence> path) {
        this.path = path;
    }

    public List<RelevanceOrder> getEntries() {
        return entries;
    }

    public void setEntries(List<RelevanceOrder> entries) {
        this.entries = entries;
    }
}
