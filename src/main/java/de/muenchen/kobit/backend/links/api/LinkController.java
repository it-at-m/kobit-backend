package de.muenchen.kobit.backend.links.api;

import de.muenchen.kobit.backend.admin.service.AdminService;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/anlaufstellen-management/links")
public class LinkController {

    private final LinkService service;
    private final AdminService adminService; // Injected AdminService

    LinkController(LinkService service, AdminService adminService) {
        this.service = service;
        this.adminService = adminService;
    }

    @GetMapping("/")
    public ResponseEntity<List<LinkView>> getLinks() {
        return ResponseEntity.ok(service.getLinks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinkView> getLinkByID(@PathVariable UUID id) {
        LinkView view = service.getLinkById(id);
        return view != null ? ResponseEntity.ok(view) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/")
    @PreAuthorize("@adminService.isUserKobitAdmin() or @adminService.isUserDepartmentAdmin()")
    public ResponseEntity<LinkView> createLink(@RequestBody final LinkView linkView) {
        return ResponseEntity.ok(service.createLink(linkView.toLink()));
    }

    @PutMapping("/")
    @PreAuthorize("@adminService.isUserKobitAdmin() or @adminService.isUserDepartmentAdmin()")
    public ResponseEntity<LinkView> setLink(@RequestParam UUID id, @RequestBody LinkView link) {
        return ResponseEntity.ok(service.editLink(id, link));
    }

    @DeleteMapping("/")
    @PreAuthorize("@adminService.isUserKobitAdmin() or @adminService.isUserDepartmentAdmin()")
    public void deleteLink(@RequestParam UUID id) {
        service.deleteById(id);
    }
}
