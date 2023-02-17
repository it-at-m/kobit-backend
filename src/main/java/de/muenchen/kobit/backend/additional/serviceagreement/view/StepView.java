package de.muenchen.kobit.backend.additional.serviceagreement.view;

import java.util.List;

public class StepView {

    private Integer stepCount;

    private String header;

    private String name;

    private Boolean hasNext;

    private List<String> optionalTexts;

    private List<TabView> tabs;

    public StepView(
            Integer stepCount,
            String header,
            String name,
            Boolean hasNext,
            List<String> optionalTexts,
            List<TabView> tabs) {
        this.stepCount = stepCount;
        this.header = header;
        this.name = name;
        this.hasNext = hasNext;
        this.optionalTexts = optionalTexts;
        this.tabs = tabs;
    }

    public Integer getStepCount() {
        return stepCount;
    }

    public void setStepCount(Integer stepCount) {
        this.stepCount = stepCount;
    }

    public List<String> getOptionalTexts() {
        return optionalTexts;
    }

    public void setOptionalTexts(List<String> optionalTexts) {
        this.optionalTexts = optionalTexts;
    }

    public List<TabView> getTabs() {
        return tabs;
    }

    public void setTabs(List<TabView> tabs) {
        this.tabs = tabs;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }
}
