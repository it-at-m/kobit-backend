package de.muenchen.kobit.backend.decisiontree.branches;

import static org.assertj.core.api.Assertions.assertThat;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.view.CompetenceView;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MobbingBranchTest {

    private MobbingBranch mobbingBranch;

    @BeforeEach
    void init() {
        mobbingBranch = new MobbingBranch();
    }

    @Test
    void getNextNodeTest_start() {
        var competence = Competence.MOBBING;
        var result = mobbingBranch.getNextNode(competence);
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

    @Test
    void getNextNodeTest_employee() {
        var competence = Competence.EMPLOYEE;
        var result = mobbingBranch.getNextNode(competence);
        assertThat(result.getAnswerOptions().size()).isEqualTo(3);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(
                                        List.of(
                                                Competence.OPPOSITE_EMPLOYEE,
                                                Competence.OPPOSITE_EXECUTIVE,
                                                Competence.OPPOSITE_JUNIOR)))
                .isTrue();
        assertThat(result.getQuestion())
                .isEqualTo("Durch wen werden Sie gemobbt/fühlen Sie sich gemobbt?");
    }

    @Test
    void getNextNodeTest_oppositeExecutive() {
        var competence = Competence.OPPOSITE_EXECUTIVE;
        var result = mobbingBranch.getNextNode(competence);
        assertThat(result.getAnswerOptions().size()).isEqualTo(3);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(
                                        List.of(
                                                Competence.STRESS_LOW,
                                                Competence.STRESS_MEDIUM,
                                                Competence.STRESS_HIGH)))
                .isTrue();
        assertThat(result.getQuestion()).isEqualTo("Wie hoch ist ihre persönliche Belastung?");
    }

    @Test
    void getNextNodeTest_end() {
        var competence = Competence.STRESS_HIGH;
        var result = mobbingBranch.getNextNode(competence);
        assertThat(result).isEqualTo(null);
    }
}
