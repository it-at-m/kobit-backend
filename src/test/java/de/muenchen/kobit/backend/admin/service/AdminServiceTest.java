package de.muenchen.kobit.backend.admin.service;

import de.muenchen.kobit.backend.user.model.User;
import de.muenchen.kobit.backend.user.service.UserDataResolver;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {

    private static final String DEPARTMENT_ADMIN = "lhm-ab-kobit-bereichsadmin";

    private static final String KOBIT_ADMIN = "lhm-ab-kobit-zentraleradmin";

    private final UserDataResolver userDataResolver = mock(UserDataResolver.class);

    @MockBean
    SecurityContextHolder securityContextHolder;

    private AdminService adminService;

    @BeforeEach
    void init() {
        adminService = new AdminService(userDataResolver);
    }

    @Test
    void isAdminTest_true() {
        when(userDataResolver.getCurrentUser())
                .thenReturn(new User("test@test.test", List.of(DEPARTMENT_ADMIN, KOBIT_ADMIN)));
        //        when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(any());
        val isAdmin = adminService.isUserAdmin();
        assertThat(isAdmin).isTrue();
    }

    @Test
    void isAdminTest_trueOnlyOne() {
        when(userDataResolver.getCurrentUser())
                .thenReturn(new User("test@test.test", List.of(KOBIT_ADMIN)));
        val isAdmin = adminService.isUserAdmin();
        assertThat(isAdmin).isTrue();
    }

    @Test
    void isAdminTest_false() {
        when(userDataResolver.getCurrentUser()).thenReturn(new User("test@test.test", List.of()));
        val isAdmin = adminService.isUserAdmin();
        assertThat(isAdmin).isFalse();
    }

    @Test
    void isKobitAdminTest_true() {
        when(userDataResolver.getCurrentUser())
                .thenReturn(new User("test@test.test", List.of(KOBIT_ADMIN)));
        val isAdmin = adminService.isUserKobitAdmin();
        assertThat(isAdmin).isTrue();
    }

    @Test
    void isKobitAdminTest_false() {
        when(userDataResolver.getCurrentUser())
                .thenReturn(new User("test@test.test", List.of(DEPARTMENT_ADMIN)));
        val isAdmin = adminService.isUserKobitAdmin();
        assertThat(isAdmin).isFalse();
    }

    @Test
    void isDepartmentAdminTest_trueDepartment() {
        when(userDataResolver.getCurrentUser())
                .thenReturn(new User("test@test.test", List.of(DEPARTMENT_ADMIN)));
        val isAdmin = adminService.isUserDepartmentAdmin();
        assertThat(isAdmin).isTrue();
    }

    @Test
    void isDepartmentAdminTest_trueKobit() {
        when(userDataResolver.getCurrentUser())
                .thenReturn(new User("test@test.test", List.of(KOBIT_ADMIN)));
        val isAdmin = adminService.isUserDepartmentAdmin();
        assertThat(isAdmin).isTrue();
    }

    @Test
    void isDepartmentAdminTest_false() {
        when(userDataResolver.getCurrentUser()).thenReturn(new User("test@test.test", List.of()));
        val isAdmin = adminService.isUserDepartmentAdmin();
        assertThat(isAdmin).isFalse();
    }
}
