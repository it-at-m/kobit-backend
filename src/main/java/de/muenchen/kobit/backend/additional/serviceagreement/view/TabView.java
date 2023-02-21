package de.muenchen.kobit.backend.additional.serviceagreement.view;

import java.util.List;

public class TabView {

    private String header;

    private List<PossibleSolutionView> possibleSolutions;

    public TabView(String header, List<PossibleSolutionView> possibleSolutions) {
        this.header = header;
        this.possibleSolutions = possibleSolutions;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<PossibleSolutionView> getPossibleSolutions() {
        return possibleSolutions;
    }

    public void setPossibleSolutions(List<PossibleSolutionView> possibleSolutions) {
        this.possibleSolutions = possibleSolutions;
    }
}
