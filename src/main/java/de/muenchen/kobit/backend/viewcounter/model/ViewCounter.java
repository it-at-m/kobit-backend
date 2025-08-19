package de.muenchen.kobit.backend.viewcounter.model;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Data
@Entity
public class ViewCounter {

    @Id
    @NotNull
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ViewCounterCategory category;

    @Column(nullable = false)
    private Long value = 0L;

    @Column()
    @Setter(AccessLevel.NONE)
    private LocalDate deactivatedAt;

    @PrePersist
    void prePersist() {
        if (this.value == null) {
            this.value = 0L;
        }
    }

    public ViewCounter deactivate() {
        this.deactivatedAt = LocalDate.now();
        return this;
    }

    public boolean isActive() {
        return this.deactivatedAt == null;
    }

    public void incrementCounter() {
        if (this.deactivatedAt == null) {
            this.value++;
        }
    }
}
