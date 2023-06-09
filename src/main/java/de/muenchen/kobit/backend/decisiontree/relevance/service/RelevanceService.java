package de.muenchen.kobit.backend.decisiontree.relevance.service;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.decisiontree.relevance.model.Path;
import de.muenchen.kobit.backend.decisiontree.relevance.model.Relevance;
import de.muenchen.kobit.backend.decisiontree.relevance.model.RelevanceCompetence;
import de.muenchen.kobit.backend.decisiontree.relevance.repository.PathRepository;
import de.muenchen.kobit.backend.decisiontree.relevance.repository.RelevanceRepository;
import de.muenchen.kobit.backend.decisiontree.relevance.view.RelevanceOrder;
import de.muenchen.kobit.backend.decisiontree.relevance.view.RelevanceView;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class RelevanceService {

    private final RelevanceRepository relevanceRepository;
    private final PathRepository pathRepository;

    RelevanceService(RelevanceRepository relevanceRepository, PathRepository pathRepository) {
        this.relevanceRepository = relevanceRepository;
        this.pathRepository = pathRepository;
    }

    public List<RelevanceOrder> getOrderOrNull(Set<Competence> competences) {
        Path path = findExistingPathOrNull(competences);
        if (path == null) {
            return null;
        } else {
            return relevanceRepository.findAllByPathId(path.getId()).stream()
                    .map(it -> new RelevanceOrder(it.getContactPointId(), it.getPosition()))
                    .collect(Collectors.toList());
        }
    }

    public void setRelevanceOrder(RelevanceView relevanceView) {
        Path matchingPath = findExistingPathOrNull(relevanceView.getPath());
        if (matchingPath == null) {
            Path newPath = createNewPath(relevanceView.getPath());
            createNewRelevanceOrder(relevanceView.getEntries(), newPath);
        } else {
            updateRelevance(relevanceView.getEntries(), matchingPath);
        }
    }

    private void updateRelevance(List<RelevanceOrder> entries, Path path) {
        relevanceRepository.deleteByPathId(path.getId());
        createNewRelevanceOrder(entries, path);
    }

    private void createNewRelevanceOrder(List<RelevanceOrder> entries, Path path) {
        entries.forEach(
                it -> {
                    relevanceRepository.save(
                            new Relevance(it.getContactPointId(), path.getId(), it.getPosition()));
                });
    }

    private Path createNewPath(Set<Competence> competences) {
        Set<RelevanceCompetence> cp =
                competences.stream()
                        .map(Enum::toString)
                        .map(RelevanceCompetence::new)
                        .collect(Collectors.toSet());
        return pathRepository.save(new Path(cp));
    }

    private Path findExistingPathOrNull(Set<Competence> selectedPath) {
        List<Path> existingPaths = pathRepository.findAll();
        List<Path> matchingPaths =
                existingPaths.stream()
                        .filter(it -> it.getCompetences().containsAll(selectedPath))
                        .collect(Collectors.toList());
        if (matchingPaths.size() > 0) {
            return matchingPaths.stream()
                    .findFirst()
                    .orElseThrow(
                            () ->
                                    new IllegalStateException(
                                            "The Optional should never be empty here!"));
        } else {
            return null;
        }
    }
}
