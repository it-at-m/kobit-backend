package de.muenchen.kobit.backend.competence.service;

import static java.util.stream.Collectors.*;

import de.muenchen.kobit.backend.competence.Competence;
import de.muenchen.kobit.backend.competence.model.CompetenceToContactPoint;
import de.muenchen.kobit.backend.competence.repository.CompetenceRepository;
import de.muenchen.kobit.backend.contact.service.ContactPointToViewMapper;
import de.muenchen.kobit.backend.contactpoint.repository.ContactPointRepository;
import de.muenchen.kobit.backend.contactpoint.view.ContactPointView;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import de.muenchen.kobit.backend.user.service.UserDataResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompetenceService {

    private final CompetenceRepository competenceRepository;
    private final ContactPointRepository contactPointRepository;
    private final ContactPointToViewMapper mapper;

    @Autowired
    UserDataResolver userDataResolver;

    CompetenceService(
            CompetenceRepository competenceRepository,
            ContactPointRepository contactPointRepository,
            ContactPointToViewMapper mapper) {
        this.competenceRepository = competenceRepository;
        this.contactPointRepository = contactPointRepository;
        this.mapper = mapper;
    }

    public List<ContactPointView> findAllContactPointsForCompetences(List<Competence> competences) {
        String department = "";
        Set<UUID> keys = getContactPointIds(competences);
        if (isSpecialCase(competences)) {
            keys.addAll(specialCaseContactPoints(competences));
        }
        return contactPointRepository.findAllById(keys).stream()
                .filter(it -> it.getDepartment().equals(department))
                .map(mapper::contactPointToView)
                .collect(Collectors.toList());
    }

    public void deleteCompetencesByContactPointId(UUID contactPointId) {
        competenceRepository.deleteAllByContactPointId(contactPointId);
    }

    public void createCompetenceToContactPoint(UUID contactPointId, Competence competence) {
        createCompetenceToContactPoint(new CompetenceToContactPoint(contactPointId, competence));
    }

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
            if (contactPointToCompetences.get(key).containsAll(competences)) {
                matchingKeys.add(key);
            }
        }
        return matchingKeys;
    }

    /**
     * @param competences - list of competences selected in the decision tree
     * @return true if a special case is given A special case are some contact points for juniors.
     *     These are not only responsible for the Juniors, they are also responsible if an executive
     *     or employee has a problem with a junior.
     */
    private boolean isSpecialCase(List<Competence> competences) {
        return ((competences.contains(Competence.EXECUTIVE)
                        && competences.contains(Competence.OPPOSITE_JUNIOR))
                || (competences.contains(Competence.EMPLOYEE)
                        && competences.contains(Competence.OPPOSITE_JUNIOR)));
    }

    private Set<UUID> specialCaseContactPoints(List<Competence> competences) {
        if (competences.contains(Competence.EXECUTIVE)
                && competences.contains(Competence.OPPOSITE_JUNIOR)) {
            competences.remove(Competence.EXECUTIVE);
            competences.add(Competence.EXECUTIVE_JUNIOR);
        }
        if (competences.contains(Competence.EMPLOYEE)
                && competences.contains(Competence.OPPOSITE_JUNIOR)) {
            competences.remove(Competence.EMPLOYEE);
            competences.add(Competence.EMPLOYEE_JUNIOR);
        }
        return getContactPointIds(competences);
    }
}
