package de.muenchen.kobit.backend.email.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String from;
    private List<String> to;
    private String subject;
    private String message;
    private boolean isReleasedFromConfidentiality;
}
