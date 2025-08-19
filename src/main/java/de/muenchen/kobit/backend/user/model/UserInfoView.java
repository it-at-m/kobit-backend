package de.muenchen.kobit.backend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@AllArgsConstructor
public class UserInfoView {

    private String department;

    private String dn;

    public UserInfoView() {}
}
