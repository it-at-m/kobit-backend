package de.muenchen.kobit.backend.links.api;

import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import java.util.List;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/anlaufstellen-management/links")
public class LinkController {

    private final LinkService service;

    LinkController(LinkService service) {
        this.service = service;
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
    public ResponseEntity<LinkView> createLink(@RequestBody final LinkView linkView) {
        return ResponseEntity.ok(service.createLink(linkView.toLink()));
    }

    @PutMapping("/")
    public ResponseEntity<LinkView> setLink(@RequestParam UUID id, @RequestBody LinkView link) {
        return ResponseEntity.ok(service.editLink(id, link));
    }

    @DeleteMapping("/")
    public void deleteLink(@RequestParam UUID id) {
        service.deleteById(id);
    }
}
