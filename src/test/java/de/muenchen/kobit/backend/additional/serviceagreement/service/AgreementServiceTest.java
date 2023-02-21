package de.muenchen.kobit.backend.additional.serviceagreement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.clearAllCaches;
import static org.mockito.Mockito.when;

import de.muenchen.kobit.backend.additional.serviceagreement.UnknownStepException;
import de.muenchen.kobit.backend.additional.serviceagreement.model.OptionalText;
import de.muenchen.kobit.backend.additional.serviceagreement.model.PossibleSolution;
import de.muenchen.kobit.backend.additional.serviceagreement.model.Step;
import de.muenchen.kobit.backend.additional.serviceagreement.model.Tab;
import de.muenchen.kobit.backend.additional.serviceagreement.repository.OptionalTextRepository;
import de.muenchen.kobit.backend.additional.serviceagreement.repository.PossibleSolutionRepository;
import de.muenchen.kobit.backend.additional.serviceagreement.repository.StepRepository;
import de.muenchen.kobit.backend.additional.serviceagreement.repository.TabRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AgreementServiceTest {

    private final OptionalTextRepository optionalTextRepository =
            Mockito.mock(OptionalTextRepository.class);
    private final PossibleSolutionRepository possibleSolutionRepository =
            Mockito.mock(PossibleSolutionRepository.class);

    private final TabRepository tabRepository = Mockito.mock(TabRepository.class);
    private final StepRepository stepRepository = Mockito.mock(StepRepository.class);

    private AgreementService agreementService;

    @BeforeEach
    void init() {
        clearAllCaches();
        agreementService =
                new AgreementService(
                        optionalTextRepository,
                        possibleSolutionRepository,
                        stepRepository,
                        tabRepository);
    }

    @Test
    void getContentForStepTest_Error() {
        Integer stepCount = 1;
        when(stepRepository.getStepByPosition(stepCount)).thenReturn(Optional.empty());
        Exception result =
                assertThrows(
                        UnknownStepException.class,
                        () -> agreementService.getContentForStep(stepCount));
        assertThat(result.getMessage()).isEqualTo("StepCount: " + stepCount + " is invalid");
    }

    @Test
    void getContentForStepTest() throws UnknownStepException {
        Integer stepCount = 1;
        Step step = new Step(UUID.randomUUID(), stepCount, "header", "name", false);
        PossibleSolution possibleSolution =
                new PossibleSolution(UUID.randomUUID(), "solution", "possible", step.getId());
        OptionalText optionalText =
                new OptionalText(UUID.randomUUID(), step.getId(), "text optional");
        Tab tab = new Tab(UUID.randomUUID(), step.getId(), "tab");
        when(stepRepository.getStepByPosition(stepCount)).thenReturn(Optional.of(step));
        when(tabRepository.findAllByStepId(step.getId())).thenReturn(List.of(tab));
        when(possibleSolutionRepository.findAllByTabId(tab.getId()))
                .thenReturn(List.of(possibleSolution));
        when(optionalTextRepository.findAllByStepId(step.getId()))
                .thenReturn(List.of(optionalText));

        var result = agreementService.getContentForStep(stepCount);

        assertThat(result.getStepCount()).isEqualTo(stepCount);
        assertThat(result.getOptionalTexts().get(0)).isEqualTo(optionalText.getText());
        assertThat(result.getTabs()).hasSize(1);
        assertThat(result.getTabs().get(0).getHeader()).isEqualTo(tab.getHeader());
        assertThat(result.getTabs().get(0).getPossibleSolutions()).hasSize(1);
        assertThat(result.getTabs().get(0).getPossibleSolutions().get(0).getText())
                .isEqualTo(possibleSolution.getText());
    }
}
