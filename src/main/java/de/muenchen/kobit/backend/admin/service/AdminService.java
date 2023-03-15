package de.muenchen.kobit.backend.admin.service;

import de.muenchen.kobit.backend.user.service.UserDataResolver;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private static final String DEPARTMENT_ADMIN = "lhm-ab-kobit-bereichsadmin";

    private static final String KOBIT_ADMIN = "lhm-ab-kobit-zentraleradmin";

    private final UserDataResolver userDataResolver;

    AdminService(UserDataResolver userDataResolver) {
        this.userDataResolver = userDataResolver;
    }

    public boolean isUserAdmin() {
        return getUserRoles()
                .stream()
                .anyMatch(it ->
                        it.equals(DEPARTMENT_ADMIN) || it.equals(KOBIT_ADMIN)
                );
    }

    public boolean isUserKobitAdmin() {
        return getUserRoles()
                .stream()
                .anyMatch(it ->
                        it.equals(KOBIT_ADMIN)
                );
    }

    public boolean isUserKobitAdmin(List<String> userRoles) {
        return userRoles
                .stream()
                .anyMatch(it ->
                        it.equals(KOBIT_ADMIN)
                );
    }

    public boolean isUserDepartmentAdmin() {
        List<String> userRoles = getUserRoles();
        return userRoles
                .stream()
                .anyMatch(it ->
                        it.equals(DEPARTMENT_ADMIN)
                ) || isUserKobitAdmin(userRoles);
    }

    private List<String> getUserRoles() {
        return userDataResolver.getCurrentUser().getRoles();
    }
}
