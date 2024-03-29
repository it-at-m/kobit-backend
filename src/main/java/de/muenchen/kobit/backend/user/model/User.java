package de.muenchen.kobit.backend.user.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class User {
    private final String email;

    private final String department;

    private final List<String> roles;
}
