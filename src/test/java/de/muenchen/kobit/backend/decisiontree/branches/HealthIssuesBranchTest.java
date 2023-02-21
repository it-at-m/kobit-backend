package de.muenchen.kobit.backend.decisiontree.branches;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import de.muenchen.kobit.backend.competence.Competence;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class HealthIssuesBranchTest {

    private HealthIssuesBranch healthIssuesBranch;

    @BeforeEach
    void init() {
        healthIssuesBranch = new HealthIssuesBranch();
    }

    @Test
    void getNextNodeTest_end() {
        var competence = Competence.PHYSICAL;
        var result = healthIssuesBranch.getNextNode(competence);

        assertThat(result).isEqualTo(null);
    }

    @Test
    void getNextNodeTest() {
        var competence = Competence.HEALTH_PROBLEMS;
        var result = healthIssuesBranch.getNextNode(competence);

        assertThat(result.getQuestion()).isEqualTo("Welches gesundheitliches Problem haben Sie?");
        assertThat(result.getAnswerOptions().size()).isEqualTo(3);
    }
}
