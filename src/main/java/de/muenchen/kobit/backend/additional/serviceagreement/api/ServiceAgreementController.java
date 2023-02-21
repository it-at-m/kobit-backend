package de.muenchen.kobit.backend.additional.serviceagreement.api;

import de.muenchen.kobit.backend.additional.serviceagreement.UnknownStepException;
import de.muenchen.kobit.backend.additional.serviceagreement.service.AgreementService;
import de.muenchen.kobit.backend.additional.serviceagreement.view.StepView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service-agreement")
public class ServiceAgreementController {

    private final AgreementService agreementService;

    ServiceAgreementController(AgreementService agreementService) {
        this.agreementService = agreementService;
    }

    @GetMapping("/{step-count}")
    StepView getInformationForStep(@PathVariable("step-count") Integer stepCount)
            throws UnknownStepException {
        return agreementService.getContentForStep(stepCount);
    }

    @GetMapping("/steps")
    Integer getLastStepCount() {
        return agreementService.getLastPossibleStep();
    }
}
