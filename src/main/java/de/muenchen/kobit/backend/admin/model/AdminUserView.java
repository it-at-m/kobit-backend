package de.muenchen.kobit.backend.admin.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AdminUserView {

    private boolean isCentralAdmin;
    private boolean isDepartmentAdmin;
    private String department;

    public AdminUserView(boolean isCentralAdmin, boolean isDepartmentAdmin, String department) {
        this.isCentralAdmin = isCentralAdmin;
        this.isDepartmentAdmin = isDepartmentAdmin;
        this.department = department;
    }

    public AdminUserView() {}

    @JsonProperty("isCentralAdmin")
    public boolean isCentralAdmin() {
        return isCentralAdmin;
    }

    public void setCentralAdmin(boolean centralAdmin) {
        isCentralAdmin = centralAdmin;
    }

    @JsonProperty("isDepartmentAdmin")
    public boolean isDepartmentAdmin() {
        return isDepartmentAdmin;
    }

    public void setDepartmentAdmin(boolean departmentAdmin) {
        isDepartmentAdmin = departmentAdmin;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
