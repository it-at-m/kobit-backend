package de.muenchen.kobit.backend.decisiontree.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.service.CompetenceService;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import de.muenchen.kobit.backend.decisiontree.branches.*;
import de.muenchen.kobit.backend.decisiontree.view.DecisionPoint;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DecisionTreeServiceTest {

    private final WorkConflictBranch workConflictBranch = mock(WorkConflictBranch.class);
    private final MobbingBranch mobbingBranch = mock(MobbingBranch.class);
    private final DiscriminationBranch discriminationBranch = mock(DiscriminationBranch.class);
    private final HealthIssuesBranch healthIssuesBranch = mock(HealthIssuesBranch.class);
    private final Root root = mock(Root.class);
    private final CompetenceService competenceService = mock(CompetenceService.class);

    private DecisionTreeService decisionTreeService;

    @BeforeEach
    void init() {
        clearAllCaches();
        decisionTreeService =
                new DecisionTreeService(
                        workConflictBranch,
                        mobbingBranch,
                        discriminationBranch,
                        healthIssuesBranch,
                        root,
                        competenceService);
    }

    @Test
    void getNextDecisionPointOrContactPointsTest_decisionPoint() {
        String department = "ITM";
        var competences = List.of(Competence.HEALTH_PROBLEMS);
        var decisionPoint =
                new DecisionPoint(
                        Competence.HEALTH_PROBLEMS,
                        "question",
                        List.of(Competence.ADDICTION.toCompetenceView()));
        when(healthIssuesBranch.getNextNode(Competence.HEALTH_PROBLEMS)).thenReturn(decisionPoint);
        var result =
                decisionTreeService.getNextDecisionPointOrContactPoints(competences, department);
        verify(competenceService, never()).findAllContactPointsForCompetences(any(), any());
        verify(mobbingBranch, never()).getNextNode(any());
        verify(discriminationBranch, never()).getNextNode(any());
        verify(workConflictBranch, never()).getNextNode(any());
        assertThat(result.getContactPoints()).isEqualTo(null);
        assertThat(result.getDecisionPoint()).isEqualTo(decisionPoint);
    }

    @Test
    void getNextDecisionPointOrContactPointsTest_empty() {
        String department = "ITM";
        List<Competence> competences = List.of();
        var decisionPoint =
                new DecisionPoint(
                        Competence.HEALTH_PROBLEMS,
                        "question",
                        List.of(Competence.ADDICTION.toCompetenceView()));
        when(root.getRootOptions()).thenReturn(decisionPoint);
        var result =
                decisionTreeService.getNextDecisionPointOrContactPoints(competences, department);
        verify(competenceService, never()).findAllContactPointsForCompetences(any(), any());
        verify(mobbingBranch, never()).getNextNode(any());
        verify(discriminationBranch, never()).getNextNode(any());
        verify(workConflictBranch, never()).getNextNode(any());
        assertThat(result.getContactPoints()).isEqualTo(null);
        assertThat(result.getDecisionPoint()).isEqualTo(decisionPoint);
    }

    @Test
    void getRootTest() {
        var decisionPoint =
                new DecisionPoint(
                        Competence.HEALTH_PROBLEMS,
                        "question",
                        List.of(Competence.ADDICTION.toCompetenceView()));
        when(root.getRootOptions()).thenReturn(decisionPoint);
        var result = decisionTreeService.getRoot();
        assertThat(result.getDecisionPoint()).isEqualTo(decisionPoint);
    }

    @Test
    void getNextDecisionPointOrContactPointsTest_end() {
        String department = "ITM";
        var id = UUID.randomUUID();
        var contactPoints = new ArrayList<ContactPointView>();
        contactPoints.add(
                new ContactPointView(
                        id, "test", "test", "test", "test", List.of(), List.of(), List.of()));
        contactPoints.add(
                new ContactPointView(
                        id, "test", "atest", "test", "test", List.of(), List.of(), List.of()));
        List<Competence> competences = List.of(Competence.ANTI_DEMOCRACY);
        when(competenceService.findAllContactPointsForCompetences(competences, department))
                .thenReturn(contactPoints);
        var result =
                decisionTreeService.getNextDecisionPointOrContactPoints(competences, department);
        verify(healthIssuesBranch, never()).getNextNode(any());
        verify(mobbingBranch, never()).getNextNode(any());
        verify(discriminationBranch, never()).getNextNode(any());
        verify(workConflictBranch, never()).getNextNode(any());
        assertThat(result.getDecisionPoint()).isEqualTo(null);
        assertThat(result.getContactPoints()).isEqualTo(contactPoints);
    }

    @Test
    void getNextDecisionPointOrContactPointsTest_end_umlaut_sorted_alphabetical() {
        String department = "ITM";
        var id = UUID.randomUUID();
        var contactPoints = new ArrayList<ContactPointView>();
        contactPoints.add(
                new ContactPointView(
                        id, "mtest", "mtest", "test", "test", List.of(), List.of(), List.of()));
        contactPoints.add(
                new ContactPointView(
                        id, "test", "test", "test", "test", List.of(), List.of(), List.of()));
        contactPoints.add(
                new ContactPointView(
                        id, "ätest", "ätest", "test", "test", List.of(), List.of(), List.of()));
        contactPoints.add(
                new ContactPointView(
                        id, "ötest", "ötest", "test", "test", List.of(), List.of(), List.of()));
        contactPoints.add(
                new ContactPointView(
                        id, "ptest", "ptest", "test", "test", List.of(), List.of(), List.of()));
        contactPoints.add(
                new ContactPointView(
                        id, "atest", "atest", "test", "test", List.of(), List.of(), List.of()));

        var contactPointsAlphabetical = new ArrayList<ContactPointView>();
        contactPointsAlphabetical.add(
                new ContactPointView(
                        id, "atest", "atest", "test", "test", List.of(), List.of(), List.of()));
        contactPointsAlphabetical.add(
                new ContactPointView(
                        id, "ätest", "ätest", "test", "test", List.of(), List.of(), List.of()));
        contactPointsAlphabetical.add(
                new ContactPointView(
                        id, "mtest", "mtest", "test", "test", List.of(), List.of(), List.of()));
        contactPointsAlphabetical.add(
                new ContactPointView(
                        id, "ötest", "ötest", "test", "test", List.of(), List.of(), List.of()));
        contactPointsAlphabetical.add(
                new ContactPointView(
                        id, "ptest", "ptest", "test", "test", List.of(), List.of(), List.of()));
        contactPointsAlphabetical.add(
                new ContactPointView(
                        id, "test", "test", "test", "test", List.of(), List.of(), List.of()));

        List<Competence> competences = List.of(Competence.ANTI_DEMOCRACY);
        when(competenceService.findAllContactPointsForCompetences(competences, department))
                .thenReturn(contactPoints);
        var result =
                decisionTreeService.getNextDecisionPointOrContactPoints(competences, department);
        verify(healthIssuesBranch, never()).getNextNode(any());
        verify(mobbingBranch, never()).getNextNode(any());
        verify(discriminationBranch, never()).getNextNode(any());
        verify(workConflictBranch, never()).getNextNode(any());
        assertThat(result.getDecisionPoint()).isEqualTo(null);
        assertThat(result.getContactPoints().get(0).getShortCut())
                .isEqualTo(contactPointsAlphabetical.get(0).getShortCut());
        assertThat(result.getContactPoints().get(1).getShortCut())
                .isEqualTo(contactPointsAlphabetical.get(1).getShortCut());
        assertThat(result.getContactPoints().get(2).getShortCut())
                .isEqualTo(contactPointsAlphabetical.get(2).getShortCut());
        assertThat(result.getContactPoints().get(3).getShortCut())
                .isEqualTo(contactPointsAlphabetical.get(3).getShortCut());
        assertThat(result.getContactPoints().get(4).getShortCut())
                .isEqualTo(contactPointsAlphabetical.get(4).getShortCut());
        assertThat(result.getContactPoints().get(5).getShortCut())
                .isEqualTo(contactPointsAlphabetical.get(5).getShortCut());
    }
}
