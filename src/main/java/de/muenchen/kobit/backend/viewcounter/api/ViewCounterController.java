package de.muenchen.kobit.backend.viewcounter.api;

import de.muenchen.kobit.backend.viewcounter.model.ViewCounterCategory;
import de.muenchen.kobit.backend.viewcounter.service.ViewCounterService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/viewcounter")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ViewCounterController {

    private final ViewCounterService viewCounterService;

    @GetMapping("/{category}")
    @PreAuthorize("@adminService.isUserKobitAdmin()")
    public Long viewCounter(@PathVariable ViewCounterCategory category) {
        return viewCounterService.getCurrentViewCounterValue(category);

    }

    @GetMapping("/sum/{category}")
    @PreAuthorize("@adminService.isUserKobitAdmin()")
    public Long viewCounterSummarized(@PathVariable ViewCounterCategory category) {
        return viewCounterService.getViewCountsByCategory(category);

    }
}
