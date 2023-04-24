package de.muenchen.kobit.backend.competence.service;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.model.CompetenceToContactPoint;
import de.muenchen.kobit.backend.competence.repository.CompetenceRepository;
import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearAllCaches;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CompetenceServiceTest {

    private final CompetenceRepository competenceRepository = mock(CompetenceRepository.class);

    private final ContactPointRepository contactPointRepository =
            mock(ContactPointRepository.class);

    private final ContactPointToViewMapper mapper = mock(ContactPointToViewMapper.class);

    private CompetenceService service;

    @BeforeEach
    void init() {
        service = new CompetenceService(competenceRepository, contactPointRepository, mapper);
    }

    @Test
    void testFindAllContactPointsForCompetences() {
        var matchId = randomUUID();
        var matchId1 = randomUUID();
        var matchId2 = randomUUID();
        var noMatch = randomUUID();
        var noMatch1 = randomUUID();

        var competences =
                List.of(Competence.DISCRIMINATION, Competence.EMPLOYEE, Competence.ETHNIC_RACIAL);
        var competenceToContactPoints =
                List.of(
                        new CompetenceToContactPoint(matchId, Competence.DISCRIMINATION),
                        new CompetenceToContactPoint(matchId, Competence.EMPLOYEE),
                        new CompetenceToContactPoint(matchId, Competence.ETHNIC_RACIAL),
                        new CompetenceToContactPoint(matchId, Competence.SEXUAL_HARASSMENT),
                        new CompetenceToContactPoint(matchId, Competence.JUNIOR),
                        new CompetenceToContactPoint(matchId1, Competence.DISCRIMINATION),
                        new CompetenceToContactPoint(matchId1, Competence.EMPLOYEE),
                        new CompetenceToContactPoint(matchId1, Competence.ETHNIC_RACIAL),
                        new CompetenceToContactPoint(matchId1, Competence.WORKPLACE_CONFLICT),
                        new CompetenceToContactPoint(matchId1, Competence.OPPOSITE_EMPLOYEE),
                        new CompetenceToContactPoint(matchId2, Competence.DISCRIMINATION),
                        new CompetenceToContactPoint(matchId2, Competence.EMPLOYEE),
                        new CompetenceToContactPoint(matchId2, Competence.ETHNIC_RACIAL),
                        new CompetenceToContactPoint(noMatch, Competence.ETHNIC_RACIAL),
                        new CompetenceToContactPoint(noMatch, Competence.EMPLOYEE),
                        new CompetenceToContactPoint(noMatch, Competence.WORKPLACE_CONFLICT),
                        new CompetenceToContactPoint(noMatch1, Competence.MOBBING),
                        new CompetenceToContactPoint(noMatch1, Competence.EMPLOYEE));
        var contactPoint = new ContactPoint(matchId, "test", "t", "test", "ITM");
        var contactPoint1 = new ContactPoint(matchId1, "test1", "t1", "test1", "ITM");
        var contactPoint2 = new ContactPoint(matchId2, "test2", "t2", "test2", "ITM");
        var contactPoints = List.of(contactPoint, contactPoint1, contactPoint2);
        var view =
                new ContactPointView(
                        matchId,
                        "test",
                        "t",
                        "test",
                        "",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        var view1 =
                new ContactPointView(
                        matchId1,
                        "test1",
                        "t1",
                        "test1",
                        "",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        var view2 =
                new ContactPointView(
                        matchId2,
                        "test2",
                        "t2",
                        "test2",
                        "",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        var contactPointViews = List.of(view, view1, view2);
        when(competenceRepository.findAllByCompetenceIn(competences))
                .thenReturn(competenceToContactPoints);
        when(contactPointRepository.findAllById(any())).thenReturn(contactPoints);
        when(mapper.contactPointToView(contactPoint)).thenReturn(view);
        when(mapper.contactPointToView(contactPoint1)).thenReturn(view1);
        when(mapper.contactPointToView(contactPoint2)).thenReturn(view2);

        var result = service.findAllContactPointsForCompetences(competences, "ITM");

        assertThat(result.size()).isEqualTo(contactPointViews.size());
        assertThat(result.containsAll(contactPointViews)).isTrue();
    }

    @Test
    void testFindAllContactPointsForCompetences_with_edge_case() {
        var matchId = randomUUID();
        var matchId1 = randomUUID();
        var matchId2 = randomUUID();
        var noMatch = randomUUID();
        var noMatch1 = randomUUID();
        var department = "ITM";

        var competences =
                List.of(
                        Competence.WORKPLACE_CONFLICT,
                        Competence.EMPLOYEE,
                        Competence.JUNIOR,
                        Competence.STRESS_MEDIUM,
                        Competence.ESCALATION_MEDIUM);
        var competenceToContactPoints =
                List.of(
                        new CompetenceToContactPoint(matchId, Competence.DISCRIMINATION),
                        new CompetenceToContactPoint(matchId, Competence.EMPLOYEE),
                        new CompetenceToContactPoint(matchId, Competence.JUNIOR),
                        new CompetenceToContactPoint(matchId, Competence.STRESS_MEDIUM),
                        new CompetenceToContactPoint(matchId, Competence.ESCALATION_MEDIUM),
                        new CompetenceToContactPoint(matchId, Competence.ETHNIC_RACIAL),
                        new CompetenceToContactPoint(matchId, Competence.SEXUAL_HARASSMENT),
                        new CompetenceToContactPoint(matchId, Competence.JUNIOR),
                        new CompetenceToContactPoint(matchId1, Competence.DISCRIMINATION),
                        new CompetenceToContactPoint(matchId1, Competence.EMPLOYEE),
                        new CompetenceToContactPoint(matchId1, Competence.ETHNIC_RACIAL),
                        new CompetenceToContactPoint(matchId1, Competence.WORKPLACE_CONFLICT),
                        new CompetenceToContactPoint(matchId1, Competence.OPPOSITE_EMPLOYEE),
                        new CompetenceToContactPoint(matchId2, Competence.DISCRIMINATION),
                        new CompetenceToContactPoint(matchId, Competence.EMPLOYEE_JUNIOR),
                        new CompetenceToContactPoint(matchId, Competence.JUNIOR),
                        new CompetenceToContactPoint(matchId, Competence.STRESS_MEDIUM),
                        new CompetenceToContactPoint(matchId, Competence.ESCALATION_MEDIUM),
                        new CompetenceToContactPoint(matchId2, Competence.ETHNIC_RACIAL),
                        new CompetenceToContactPoint(noMatch, Competence.ETHNIC_RACIAL),
                        new CompetenceToContactPoint(noMatch, Competence.EMPLOYEE),
                        new CompetenceToContactPoint(noMatch, Competence.WORKPLACE_CONFLICT),
                        new CompetenceToContactPoint(noMatch1, Competence.MOBBING),
                        new CompetenceToContactPoint(noMatch1, Competence.EMPLOYEE));
        var contactPoint = new ContactPoint(matchId, "test", "t", "test", department);
        var contactPoint1 = new ContactPoint(matchId1, "test1", "t1", "test1", department);
        var contactPoint2 = new ContactPoint(matchId2, "test2", "t2", "test2", department);
        var contactPoints = List.of(contactPoint, contactPoint1, contactPoint2);
        var view =
                new ContactPointView(
                        matchId,
                        "test",
                        "t",
                        "test",
                        "",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        var view1 =
                new ContactPointView(
                        matchId1,
                        "test1",
                        "t1",
                        "test1",
                        "",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        var view2 =
                new ContactPointView(
                        matchId2,
                        "test2",
                        "t2",
                        "test2",
                        "",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        var contactPointViews = List.of(view, view1, view2);
        when(competenceRepository.findAllByCompetenceIn(competences))
                .thenReturn(competenceToContactPoints);
        when(contactPointRepository.findAllById(any())).thenReturn(contactPoints);
        when(mapper.contactPointToView(contactPoint)).thenReturn(view);
        when(mapper.contactPointToView(contactPoint1)).thenReturn(view1);
        when(mapper.contactPointToView(contactPoint2)).thenReturn(view2);

        var result = service.findAllContactPointsForCompetences(competences, department);

        assertThat(result.size()).isEqualTo(contactPointViews.size());
        assertThat(result.containsAll(contactPointViews)).isTrue();
    }
}
