package de.muenchen.kobit.backend.additional.serviceagreement.service;

import de.muenchen.kobit.backend.additional.serviceagreement.UnknownStepException;
import de.muenchen.kobit.backend.additional.serviceagreement.model.OptionalText;
import de.muenchen.kobit.backend.additional.serviceagreement.model.PossibleSolution;
import de.muenchen.kobit.backend.additional.serviceagreement.model.Step;
import de.muenchen.kobit.backend.additional.serviceagreement.repository.OptionalTextRepository;
import de.muenchen.kobit.backend.additional.serviceagreement.repository.PossibleSolutionRepository;
import de.muenchen.kobit.backend.additional.serviceagreement.repository.StepRepository;
import de.muenchen.kobit.backend.additional.serviceagreement.repository.TabRepository;
import de.muenchen.kobit.backend.additional.serviceagreement.view.PossibleSolutionView;
import de.muenchen.kobit.backend.additional.serviceagreement.view.StepView;
import de.muenchen.kobit.backend.additional.serviceagreement.view.TabView;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AgreementService {

    private final OptionalTextRepository optionalTextRepository;
    private final PossibleSolutionRepository possibleSolutionRepository;
    private final StepRepository stepRepository;
    private final TabRepository tabRepository;

    AgreementService(
            OptionalTextRepository optionalTextRepository,
            PossibleSolutionRepository possibleSolutionRepository,
            StepRepository stepRepository,
            TabRepository tabRepository) {
        this.optionalTextRepository = optionalTextRepository;
        this.possibleSolutionRepository = possibleSolutionRepository;
        this.stepRepository = stepRepository;
        this.tabRepository = tabRepository;
    }

    public Integer getLastPossibleStep() {
        return stepRepository.findAll().stream()
                .map(Step::getPosition)
                .max(Integer::compare)
                .orElseThrow(IllegalStateException::new);
    }

    public StepView getContentForStep(Integer stepCount) throws UnknownStepException {
        Step step = getStepOrThrow(stepCount);
        List<TabView> tabViews = getTabViews(step);
        return new StepView(
                step.getPosition(),
                step.getHeader(),
                step.getName(),
                step.getHasNext(),
                getOptionalTextsFromStep(step.getId()),
                tabViews);
    }

    private List<TabView> getTabViews(Step step) {
        return tabRepository.findAllByStepId(step.getId()).stream()
                .map(it -> it.toView(getPossibleSolutionsForTab(it.getId())))
                .collect(Collectors.toList());
    }

    private Step getStepOrThrow(Integer stepCount) throws UnknownStepException {
        return stepRepository
                .getStepByPosition(stepCount)
                .orElseThrow(
                        () -> new UnknownStepException("StepCount: " + stepCount + " is invalid"));
    }

    private List<PossibleSolutionView> getPossibleSolutionsForTab(UUID tabId) {
        return possibleSolutionRepository.findAllByTabId(tabId).stream()
                .map(PossibleSolution::toView)
                .collect(Collectors.toList());
    }

    private List<String> getOptionalTextsFromStep(UUID stepId) {
        return optionalTextRepository.findAllByStepId(stepId).stream()
                .map(OptionalText::getText)
                .collect(Collectors.toList());
    }
}
