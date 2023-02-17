package de.muenchen.kobit.backend.anlaufstellenmanagement.links.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.atMostOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import de.muenchen.kobit.backend.links.model.Link;
import de.muenchen.kobit.backend.links.repo.LinkRepository;
import de.muenchen.kobit.backend.links.service.LinkService;
import de.muenchen.kobit.backend.links.view.LinkView;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LinkServiceTest {

    private final LinkRepository repo = Mockito.mock(LinkRepository.class);

    private LinkService service;

    @BeforeEach
    void init() {
        Mockito.reset(repo);
        service = new LinkService(repo);
    }

    @Test
    void testGet() {
        final var link = new Link(UUID.randomUUID(), "link", "link", false);
        Mockito.when(repo.findAll()).thenReturn(List.of(link));
        final var result = service.getLinks();
        assertEquals(result.size(), 1);
    }

    @Test
    void testEditNotPresent() {
        final var id = UUID.randomUUID();
        final var view = new LinkView(UUID.randomUUID(), "name", "url", false);
        Mockito.when(repo.findById(id)).thenReturn(Optional.empty());
        Mockito.when(repo.save(any()))
                .thenReturn(
                        new Link(
                                UUID.randomUUID(),
                                view.getName(),
                                view.getUrl(),
                                view.isInDownloads()));
        service.editLink(id, view);
        verify(repo, never()).deleteById(UUID.randomUUID());
        verify(repo, atMost(1)).save(any());
    }

    @Test
    void testEditWriteNew() {
        final var id = UUID.randomUUID();
        final var view = new LinkView(UUID.randomUUID(), "name", "url", false);
        final var presentLink = new Link(UUID.randomUUID(), "neuer name", "neu url", false);
        Mockito.when(repo.findById(id)).thenReturn(Optional.of(presentLink));
        Mockito.when(repo.save(any()))
                .thenReturn(
                        new Link(
                                UUID.randomUUID(),
                                view.getName(),
                                view.getUrl(),
                                view.isInDownloads()));
        service.editLink(id, view);
        verify(repo, atMostOnce()).deleteById(presentLink.getId());
        verify(repo, atMostOnce()).save(any());
    }
}
