package de.muenchen.kobit.backend.admin.service;

import de.muenchen.kobit.backend.admin.model.AdminUserView;
import de.muenchen.kobit.backend.user.service.UserDataResolver;
import de.muenchen.kobit.backend.viewcounter.model.ViewCounterCategory;
import de.muenchen.kobit.backend.viewcounter.service.ViewCounterService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AdminService {

    private static final String DEPARTMENT_ADMIN = "lhm-ab-kobit-bereichsadmin";

    private static final String KOBIT_ADMIN = "lhm-ab-kobit-zentraleradmin";

    private final ViewCounterService viewCounterService;
    private final UserDataResolver userDataResolver;

    public boolean isUserAdmin() {
        boolean isAdmin =
                getUserRoles().stream()
                        .anyMatch(it -> it.equals(DEPARTMENT_ADMIN) || it.equals(KOBIT_ADMIN));

        if (isAdmin) {
            viewCounterService.incrementCounter(ViewCounterCategory.ADMIN_PAGE_VISITED_COUNTER);
        } else {
            viewCounterService.incrementCounter(ViewCounterCategory.PAGE_VISITED_COUNTER);
        }

        return isAdmin;
    }

    public AdminUserView getAdminUserInfo() {
        return new AdminUserView(
                isUserKobitAdmin(),
                isUserDepartmentAdmin(),
                userDataResolver.getCurrentUser().getDepartment());
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
