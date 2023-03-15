package de.muenchen.kobit.backend.admin.api;

import de.muenchen.kobit.backend.admin.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    boolean isUserAdmin() {
        return adminService.isUserAdmin();
    }

}
