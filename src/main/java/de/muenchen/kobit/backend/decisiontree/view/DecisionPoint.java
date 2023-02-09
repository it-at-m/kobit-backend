package de.muenchen.kobit.backend.decisiontree.view;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.view.CompetenceView;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * A node is a question in the decision tree. The possibilities are represented through the child
 * nodes.
 */
@Getter
@Setter
public class DecisionPoint {
    private final Competence competence;

    private final String question;

    private final List<CompetenceView> answerOptions;

    public DecisionPoint(
            Competence competence, String question, List<CompetenceView> answersOptions) {
        this.competence = competence;
        this.question = question;
        this.answerOptions = answersOptions;
    }
}
