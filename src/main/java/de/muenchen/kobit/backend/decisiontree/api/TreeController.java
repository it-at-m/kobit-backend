package de.muenchen.kobit.backend.decisiontree.api;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.decisiontree.service.DecisionTreeService;
import de.muenchen.kobit.backend.decisiontree.view.DecisionContactPointWrapper;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/unterstuetzungsfinder")
public class TreeController {

    private final DecisionTreeService decisionTreeService;

    TreeController(DecisionTreeService decisionTreeService) {
        this.decisionTreeService = decisionTreeService;
    }

    @GetMapping("/start")
    public DecisionContactPointWrapper getDecisionTreeStart() {
        return decisionTreeService.getRoot();
    }

    @PostMapping(value = "/next")
    public DecisionContactPointWrapper getNextDecisionOrContactPoints(
            @RequestBody List<Competence> competences) {
        return decisionTreeService.getNextDecisionPointOrContactPoints(competences);
    }
}
