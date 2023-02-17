package de.muenchen.kobit.backend.decisiontree.branches;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.view.CompetenceView;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;

class WorkConflictBranchTest {

    private WorkConflictBranch workConflictBranch;

    @BeforeEach
    void init() {
        workConflictBranch = new WorkConflictBranch();
    }

    void getNextNodeTest_start() {
        var competence = Competence.WORKPLACE_CONFLICT;
        var result = workConflictBranch.getNextNode(competence);
        assertThat(result.getAnswerOptions().size()).isEqualTo(3);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(
                                        List.of(
                                                Competence.EMPLOYEE,
                                                Competence.JUNIOR,
                                                Competence.EXECUTIVE)))
                .isTrue();
        assertThat(result.getQuestion()).isEqualTo("Welche Rolle haben Sie?");
    }

    void getNextNodeTest_junior() {
        var competence = Competence.JUNIOR;
        var result = workConflictBranch.getNextNode(competence);
        assertThat(result.getAnswerOptions().size()).isEqualTo(4);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(
                                        List.of(
                                                Competence.OPPOSITE_EMPLOYEE,
                                                Competence.OPPOSITE_EXECUTIVE,
                                                Competence.OPPOSITE_JUNIOR,
                                                Competence.OPPOSITE_OTHER_TEAM)))
                .isTrue();
        assertThat(result.getQuestion()).isEqualTo("Mit wem haben Sie den Konflikt?");
    }

    void getNextNodeTest_oppositeExecutive() {
        var competence = Competence.OPPOSITE_EXECUTIVE;
        var result = workConflictBranch.getNextNode(competence);
        assertThat(result.getAnswerOptions().size()).isEqualTo(3);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(
                                        List.of(
                                                Competence.STRESS_HIGH,
                                                Competence.STRESS_MEDIUM,
                                                Competence.STRESS_LOW)))
                .isTrue();
        assertThat(result.getQuestion()).isEqualTo("Wie hoch ist ihre persönliche Belastung?");
    }

    void getNextNodeTest_stressHigh() {
        var competence = Competence.STRESS_HIGH;
        var result = workConflictBranch.getNextNode(competence);
        assertThat(result.getAnswerOptions().size()).isEqualTo(3);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(
                                        List.of(
                                                Competence.ESCALATION_HIGH,
                                                Competence.ESCALATION_MEDIUM,
                                                Competence.ESCALATION_LOW)))
                .isTrue();
        assertThat(result.getQuestion())
                .isEqualTo("Wie stark hat sich die Situation bereits verschärft?");
    }

    void getNextNodeTest_end() {
        var competence = Competence.ESCALATION_MEDIUM;
        var result = workConflictBranch.getNextNode(competence);
        assertThat(result).isEqualTo(null);
    }
}
