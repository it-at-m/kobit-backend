package de.muenchen.kobit.backend.viewcounter.service;

import de.muenchen.kobit.backend.email.model.ReportEmailService;
import de.muenchen.kobit.backend.viewcounter.model.ViewCounter;
import de.muenchen.kobit.backend.viewcounter.model.ViewCounterCategory;
import de.muenchen.kobit.backend.viewcounter.repository.ViewCounterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ViewCounterService {

    private final ViewCounterRepository viewCounterRepository;

    private final ReportEmailService reportEmailService;

    /**
     * Increments the viewCounter by one.
     * @param category the category of the viewCounter which should be incremented.
     */
    @Transactional
    public void incrementCounter(ViewCounterCategory category) {
        ViewCounter viewCounter = viewCounterRepository.findByCategoryAndDeactivatedAtIsNull(category)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "View counter not found!"));

        viewCounter.incrementCounter();
        log.debug("Incremented {} counter to new value: {}", category, viewCounter.getValue());
        viewCounterRepository.save(viewCounter);
    }

    /**
     * Fetches the value of a viewCounter.
     * @param category the wanted viewCounter.
     * @return the value of the fetched viewCounter.
     */
    public Long getCurrentViewCounterValue(ViewCounterCategory category) {
        ViewCounter viewCounter = viewCounterRepository.findByCategoryAndDeactivatedAtIsNull(category)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "View counter not found!"));

        return viewCounter.getValue();
    }

    /**
     * Fetches all viewCounters.
     * @return the sum of all views
     */
    public Long getViewCountsByCategory(ViewCounterCategory category) {
        List<ViewCounter> viewCounterList = viewCounterRepository.findAllByCategory(category);

        return viewCounterList.stream().mapToLong(ViewCounter::getValue).sum();
    }

    public void triggerViewCounterReporting() {
        List<ViewCounter> activeViewCounters = viewCounterRepository.findAllByDeactivatedAtIsNull();
        try {
            reportEmailService.sendViewCounterReport(activeViewCounters, YearMonth.now());
        } catch (MessagingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while sending view counter report", e);
        }
    }

    @Scheduled(cron = "${kobit.mail.report-cron}", zone = "Europe/Berlin")
    public void viewCounterReporting() throws MessagingException {
        // deactivate current-viewcounters
        List<ViewCounter> activeViewCounters = viewCounterRepository.findAllByDeactivatedAtIsNull();
        activeViewCounters.forEach(ViewCounter::deactivate);

        // create new viewcounters
        Arrays.stream(ViewCounterCategory.values()).forEach(category -> {
            ViewCounter newViewCounter = new ViewCounter();
            newViewCounter.setCategory(category);
            viewCounterRepository.save(newViewCounter);
        });

        // send reportemail
        reportEmailService.sendViewCounterReport(activeViewCounters, YearMonth.now().minusMonths(1));
    }


}
