package de.muenchen.kobit.backend.admin.api;

import de.muenchen.kobit.backend.admin.model.AdminUserView;
import de.muenchen.kobit.backend.admin.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    private final AdminService adminService;

    AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    boolean isUserAdmin() {
        return adminService.isUserAdmin();
    }

    @GetMapping("/info")
    AdminUserView getUserInfo(@AuthenticationPrincipal OidcUser oidcUser) {
        log.info(String.valueOf(oidcUser.getClaims()));
        return adminService.getAdminUserInfo();
    }
}
