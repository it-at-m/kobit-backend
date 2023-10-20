package de.muenchen.kobit.backend.decisiontree.relevance.view;

import java.util.UUID;

public class RelevanceOrder implements Comparable<RelevanceOrder> {

    private UUID contactPointId;

    private Integer position;

    public RelevanceOrder(UUID contactPointId, int position) {
        this.contactPointId = contactPointId;
        this.position = position;
    }

    public UUID getContactPointId() {
        return contactPointId;
    }

    public void setContactPointId(UUID contactPointId) {
        this.contactPointId = contactPointId;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int compareTo(RelevanceOrder o) {
        return getPosition().compareTo(o.position);
    }
}
