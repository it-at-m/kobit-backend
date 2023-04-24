package de.muenchen.kobit.backend.admin.service;

import de.muenchen.kobit.backend.admin.model.AdminUserView;
import de.muenchen.kobit.backend.user.service.UserDataResolver;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private static final String DEPARTMENT_ADMIN = "lhm-ab-kobit-bereichsadmin";

    private static final String KOBIT_ADMIN = "lhm-ab-kobit-zentraleradmin";

    private final UserDataResolver userDataResolver;

    AdminService(UserDataResolver userDataResolver) {
        this.userDataResolver = userDataResolver;
    }

    public boolean isUserAdmin() {
        return getUserRoles().stream()
                .anyMatch(it -> it.equals(DEPARTMENT_ADMIN) || it.equals(KOBIT_ADMIN));
    }

    public AdminUserView getAdminUserInfo() {
        return new AdminUserView(isUserKobitAdmin(), isUserDepartmentAdmin(), userDataResolver.getCurrentUser().getDepartment());
    }

    public boolean isUserKobitAdmin() {
        return getUserRoles().stream().anyMatch(it -> it.equals(KOBIT_ADMIN));
    }

    private boolean isUserKobitAdmin(List<String> userRoles) {
        return userRoles.stream().anyMatch(it -> it.equals(KOBIT_ADMIN));
    }

    public boolean isUserDepartmentAdmin() {
        List<String> userRoles = getUserRoles();
        return userRoles.stream().anyMatch(it -> it.equals(DEPARTMENT_ADMIN))
                || isUserKobitAdmin(userRoles);
    }

    private List<String> getUserRoles() {
        return userDataResolver.getCurrentUser().getRoles();
    }
}
