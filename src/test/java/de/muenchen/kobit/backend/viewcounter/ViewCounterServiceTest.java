package de.muenchen.kobit.backend.viewcounter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import de.muenchen.kobit.backend.email.model.ReportEmailService;
import de.muenchen.kobit.backend.viewcounter.model.ViewCounter;
import de.muenchen.kobit.backend.viewcounter.model.ViewCounterCategory;
import de.muenchen.kobit.backend.viewcounter.repository.ViewCounterRepository;
import de.muenchen.kobit.backend.viewcounter.service.ViewCounterService;
import java.time.YearMonth;
import java.util.*;
import javax.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ViewCounterServiceTest {

    @Mock private ViewCounterRepository viewCounterRepository;

    @Mock private ReportEmailService reportEmailService;

    @InjectMocks private ViewCounterService viewCounterService;

    @BeforeEach
    void setup() {
        lenient()
                .when(viewCounterRepository.save(any(ViewCounter.class)))
                .thenAnswer(inv -> inv.getArgument(0));
    }

    @Test
    void testBasicCounterFunctionality() {
        ViewCounter viewCounter = new ViewCounter();
        viewCounter.setCategory(ViewCounterCategory.PAGE_VISITED_COUNTER);

        when(viewCounterRepository.findByCategoryAndDeactivatedAtIsNull(any()))
                .thenReturn(Optional.of(viewCounter));
        ArgumentCaptor<ViewCounter> captor = ArgumentCaptor.forClass(ViewCounter.class);

        viewCounterService.incrementCounter(ViewCounterCategory.PAGE_VISITED_COUNTER);

        verify(viewCounterRepository).save(captor.capture());
        ViewCounter savedViewCounter = captor.getValue();
        assertThat(savedViewCounter.getValue()).isEqualTo(1);
    }

    @Test
    void viewCounterReporting_deactivatesActiveCounters_createsNewCounters_andSendsReport()
            throws Exception {
        ViewCounter vc1 = new ViewCounter();
        vc1.setCategory(ViewCounterCategory.PAGE_VISITED_COUNTER);

        ViewCounter vc2 = new ViewCounter();
        vc2.setCategory(ViewCounterCategory.E_MAIL_SEND_COUNTER);

        List<ViewCounter> activeViewCounters = new ArrayList<>();
        activeViewCounters.add(vc1);
        activeViewCounters.add(vc2);

        when(viewCounterRepository.findAllByDeactivatedAtIsNull()).thenReturn(activeViewCounters);

        YearMonth expectedReportMonth = YearMonth.now().minusMonths(1);

        viewCounterService.viewCounterReporting();

        assertThat(vc1.getDeactivatedAt()).isNotNull();
        assertThat(vc2.getDeactivatedAt()).isNotNull();
        ArgumentCaptor<ViewCounter> saveCaptor = ArgumentCaptor.forClass(ViewCounter.class);
        verify(viewCounterRepository, times(ViewCounterCategory.values().length))
                .save(saveCaptor.capture());
        List<ViewCounter> savedNewCounters = saveCaptor.getAllValues();
        assertThat(savedNewCounters.size()).isEqualTo(ViewCounterCategory.values().length);
        Set<ViewCounterCategory> expectedCategories = EnumSet.allOf(ViewCounterCategory.class);
        Set<ViewCounterCategory> savedCategories =
                savedNewCounters.stream()
                        .map(ViewCounter::getCategory)
                        .collect(java.util.stream.Collectors.toSet());
        assertThat(savedCategories).isEqualTo(expectedCategories);

        ArgumentCaptor<YearMonth> ymCaptor = ArgumentCaptor.forClass(YearMonth.class);
        verify(reportEmailService)
                .sendViewCounterReport(eq(activeViewCounters), ymCaptor.capture());
        assertThat(ymCaptor.getValue()).isEqualTo(expectedReportMonth);
        verifyNoMoreInteractions(reportEmailService);
    }

    @Test
    void viewCounterReporting_propagatesMessagingException() throws Exception {
        when(viewCounterRepository.findAllByDeactivatedAtIsNull()).thenReturn(List.of());

        doThrow(new MessagingException("SMTP down"))
                .when(reportEmailService)
                .sendViewCounterReport(anyList(), any(YearMonth.class));

        assertThrows(MessagingException.class, () -> viewCounterService.viewCounterReporting());
    }
}
