package de.muenchen.kobit.backend.decisiontree.relevance.service;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.decisiontree.relevance.model.Path;
import de.muenchen.kobit.backend.decisiontree.relevance.model.Relevance;
import de.muenchen.kobit.backend.decisiontree.relevance.model.RelevanceCompetence;
import de.muenchen.kobit.backend.decisiontree.relevance.repository.PathRepository;
import de.muenchen.kobit.backend.decisiontree.relevance.repository.RelevanceCompetenceRepository;
import de.muenchen.kobit.backend.decisiontree.relevance.repository.RelevanceRepository;
import de.muenchen.kobit.backend.decisiontree.relevance.view.RelevanceOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RelevanceServiceTest {

    private final RelevanceRepository relevanceRepository = mock(RelevanceRepository.class);
    private final PathRepository pathRepository = mock(PathRepository.class);
    private final RelevanceCompetenceRepository relevanceCompetenceRepository = mock(RelevanceCompetenceRepository.class);

    private RelevanceService service;

    @BeforeEach
    void init() {
        service = new RelevanceService(relevanceRepository, pathRepository, relevanceCompetenceRepository);
    }

    @Test
    void getOrderTest() {
        final UUID pathId = UUID.randomUUID();
        final UUID contactPointId = UUID.randomUUID();
        final Set<Competence> competences = Set.of(Competence.DISCRIMINATION, Competence.ETHNIC_RACIAL);
        final RelevanceCompetence discrimination = new RelevanceCompetence(Competence.DISCRIMINATION.toString());
        final RelevanceCompetence ethnic = new RelevanceCompetence(Competence.ETHNIC_RACIAL.toString());
        final RelevanceCompetence antiDemocracy = new RelevanceCompetence(Competence.ANTI_DEMOCRACY.toString());
        final List<Path> paths = List.of(new Path(pathId, Set.of(discrimination, ethnic)), new Path(Set.of(antiDemocracy)));
        when(pathRepository.findAll()).thenReturn(paths);
        when(relevanceRepository.findAllByPathId(pathId)).thenReturn(List.of(new Relevance(contactPointId, pathId, 1)));

        final List<RelevanceOrder> result = service.getOrderOrNull(competences);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPosition()).isEqualTo(1);
        assertThat(result.get(0).getContactPointId()).isEqualTo(contactPointId);
    }

    @Test
    void getOrderTest_Null() {
        final RelevanceCompetence antiDemocracy = new RelevanceCompetence(Competence.ANTI_DEMOCRACY.toString());
        final Set<Competence> competences = Set.of(Competence.DISCRIMINATION, Competence.ETHNIC_RACIAL);
        final List<Path> paths = List.of(new Path(Set.of(antiDemocracy)));
        when(pathRepository.findAll()).thenReturn(paths);
        final List<RelevanceOrder> result = service.getOrderOrNull(competences);
        assertThat(result).isNull();
    }

}