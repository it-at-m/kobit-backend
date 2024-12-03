package de.muenchen.kobit.backend.competence.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.model.CompetenceToContactPoint;
import de.muenchen.kobit.backend.competence.repository.CompetenceRepository;
import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contactpoint.model.ContactPoint;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompetenceService {

    private static final Logger log = LoggerFactory.getLogger(CompetenceService.class);
    private final CompetenceRepository competenceRepository;
    private final ContactPointRepository contactPointRepository;
    private final ContactPointToViewMapper mapper;

    CompetenceService(
            CompetenceRepository competenceRepository,
            ContactPointRepository contactPointRepository,
            ContactPointToViewMapper mapper) {
        this.competenceRepository = competenceRepository;
        this.contactPointRepository = contactPointRepository;
        this.mapper = mapper;
    }

    public List<ContactPointView> findAllContactPointsForCompetences(List<UUID> foundCPs,
            List<Competence> competences, String department) {
        Set<UUID> keys = new HashSet<>(foundCPs);

        return getMatchingContactPoints(department, keys).stream()
                .map(mapper::contactPointToView)
                .collect(Collectors.toList());
    }

    public List<Competence> findAllCompetencesForId(UUID contactPointId) {
        return competenceRepository.findAllByContactPointId(contactPointId).stream()
                .map(CompetenceToContactPoint::getCompetence)
                .collect(toList());
    }

    private List<ContactPoint> getMatchingContactPoints(String department, Set<UUID> keys) {

        log.debug("getMatchingContactPoints | department {}", department);
        if (department == null) {
            return keys.stream()
                    .map(contactPointRepository::findContactPointByIdLike)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(toList());
        }
        return keys.stream()
                .map(
                        it ->
                                contactPointRepository.findContactPointByIdAndDepartmentLike(
                                        it, department))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    // Extremly hacky workaround for trashy datastructure - please improve in the future
    public Competence getCompetenceByEnumString(String enumAsString) {
        String splittedString = enumAsString.split("\\.")[1].split("\\(")[0];

        log.debug("getCompetenceByEnumString | splittedString {}", splittedString);

        return Competence.valueOf(splittedString);
    }

    @Transactional
    public void deleteCompetencesByContactPointId(UUID contactPointId) {
        competenceRepository.deleteAllByContactPointId(contactPointId);
    }

    @Transactional
    public void deleteCompetenceAndContactPointPair(
            UUID contactPointId, List<Competence> competences) {
        competences.forEach(
                it -> competenceRepository.deleteByContactPointIdAndCompetence(contactPointId, it));
    }

    @Transactional
    public void createCompetenceToContactPoint(UUID contactPointId, Competence competence) {
        createCompetenceToContactPoint(new CompetenceToContactPoint(contactPointId, competence));
    }

    @Transactional
    public void createCompetenceToContactPoint(CompetenceToContactPoint competence) {
        competenceRepository.save(competence);
    }

    private Set<UUID> getContactPointIds(List<Competence> competences) {
        Map<UUID, List<Competence>> contactPointToCompetences =
                competenceRepository.findAllByCompetenceIn(competences).stream()
                        .collect(
                                groupingBy(
                                        CompetenceToContactPoint::getContactPointId,
                                        mapping(
                                                CompetenceToContactPoint::getCompetence,
                                                toList())));
        Set<UUID> matchingKeys = new HashSet<>();
        for (UUID key : contactPointToCompetences.keySet()) {
            if (new HashSet<>(contactPointToCompetences.get(key)).containsAll(competences)) {
                matchingKeys.add(key);
            }
        }
        return matchingKeys;
    }
}
