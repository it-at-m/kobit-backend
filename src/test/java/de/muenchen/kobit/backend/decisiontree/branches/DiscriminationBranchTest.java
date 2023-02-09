package de.muenchen.kobit.backend.decisiontree.branches;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.view.CompetenceView;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DiscriminationBranchTest {

    private DiscriminationBranch discriminationBranch;

    @BeforeEach
    void init() {
        discriminationBranch = new DiscriminationBranch();
    }

    @Test
    void getNextNodeTest_start() {
        var competence = Competence.DISCRIMINATION;
        var result = discriminationBranch.getNextNode(competence);
        assertThat(result.getQuestion()).isEqualTo("Welche Rolle haben Sie?");
        assertThat(result.getAnswerOptions().size()).isEqualTo(2);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(List.of(Competence.EMPLOYEE, Competence.JUNIOR)))
                .isTrue();
    }

    @Test
    void getNextNodeTest_junior() {
        var competence = Competence.JUNIOR;
        var result = discriminationBranch.getNextNode(competence);
        assertThat(result.getQuestion())
                .isEqualTo("Aus welchem Grund werden Sie/fühlen Sie sich diskriminiert?");
        assertThat(result.getAnswerOptions().size()).isEqualTo(3);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(
                                        List.of(
                                                Competence.ETHNIC_RACIAL,
                                                Competence.DISABLED,
                                                Competence.SEXUAL_IDENTITY)))
                .isTrue();
    }

    @Test
    void getNextNodeTest_sexual() {
        var competence = Competence.SEXUAL_IDENTITY;
        var result = discriminationBranch.getNextNode(competence);
        assertThat(result.getQuestion())
                .isEqualTo("Mann-Frau Diskriminierung oder LGBTIQ* Diskriminierung?");
        assertThat(result.getAnswerOptions().size()).isEqualTo(2);
        assertThat(
                        result.getAnswerOptions().stream()
                                .map(CompetenceView::getCompetence)
                                .collect(Collectors.toList())
                                .containsAll(List.of(Competence.EQUALITY, Competence.LGBTIQ)))
                .isTrue();
    }

    @Test
    void getNextNodeTest_end() {
        var competence = Competence.DISABLED;
        var result = discriminationBranch.getNextNode(competence);
        assertThat(result).isEqualTo(null);
    }
}
