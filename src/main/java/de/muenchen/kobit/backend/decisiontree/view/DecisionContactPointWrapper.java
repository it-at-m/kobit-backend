package de.muenchen.kobit.backend.decisiontree.view;

import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecisionContactPointWrapper {
    // only one at a time is present. the other element is null
    private final DecisionPoint decisionPoint;
    private final List<ContactPointView> contactPoints;

    public DecisionContactPointWrapper(DecisionPoint decisionPoint) {
        this.decisionPoint = decisionPoint;
        this.contactPoints = null;
    }

    public DecisionContactPointWrapper(List<ContactPointView> contactPoints) {
        this.decisionPoint = null;
        this.contactPoints = contactPoints;
    }
}
