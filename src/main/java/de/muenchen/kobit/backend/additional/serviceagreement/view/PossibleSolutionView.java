package de.muenchen.kobit.backend.additional.serviceagreement.view;

public class PossibleSolutionView {

    private String header;

    private String text;

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

    public PossibleSolutionView(String header, String text) {
        this.header = header;
        this.text = text;
    }
}
