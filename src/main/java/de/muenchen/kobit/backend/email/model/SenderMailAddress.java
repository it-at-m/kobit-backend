package de.muenchen.kobit.backend.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SenderMailAddress {
    private String emailAddress;
}
