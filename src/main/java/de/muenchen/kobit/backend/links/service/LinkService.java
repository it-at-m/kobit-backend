package de.muenchen.kobit.backend.links.service;

import de.muenchen.kobit.backend.links.model.Link;
import de.muenchen.kobit.backend.links.repo.LinkRepository;
import de.muenchen.kobit.backend.links.view.LinkView;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LinkService {

    private final LinkRepository repo;

    public LinkService(LinkRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<LinkView> getLinks() {
        return repo.findAll().stream().map(this::linkToView).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LinkView getLinkById(UUID id) {
        return repo.findById(id).map(this::linkToView).orElseGet(() -> null);
    }

    @Transactional(readOnly = true)
    public LinkView createLink(Link link) {
        return linkToView(repo.save(link));
    }

    @Transactional
    public LinkView editLink(UUID id, LinkView view) {
        Optional<Link> link = repo.findById(id);
        if (link.isPresent()) {
            deleteById(link.get().getId());
            Link linkToSave = view.toLink();
            linkToSave.setId(link.get().getId());
            return createLink(linkToSave);
        } else {
            return createLink(view.toLink());
        }
    }

    @Transactional
    public void deleteById(UUID id) {
        repo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<LinkView> getLinkViewsByContactPointId(UUID id) {
        return getLinksByContactPointId(id).stream()
                .map(this::linkToView)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Link> getLinksByContactPointId(UUID contactPointId) {
        return repo.findAllByContactPointId(contactPointId);
    }

    @Transactional
    public void deleteLinkByContactPointId(UUID contactPointId) {
        repo.deleteAllByContactPointId(contactPointId);
    }

    private LinkView linkToView(Link link) {
        return new LinkView(
                link.getContactPointId(), link.getName(), link.getUrl(), link.isInDownloads());
    }
}
