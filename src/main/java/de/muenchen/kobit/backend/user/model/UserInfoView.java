package de.muenchen.kobit.backend.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoView {

    private String department;

    public UserInfoView(String department) {
        this.department = department;
    }

    public UserInfoView() {}

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
