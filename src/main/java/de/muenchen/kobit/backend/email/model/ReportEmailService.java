package de.muenchen.kobit.backend.email.model;

import de.muenchen.kobit.backend.viewcounter.model.ViewCounter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import static liquibase.util.StringUtil.escapeHtml;
import static org.apache.commons.lang3.StringUtils.capitalize;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportEmailService {
    private final JavaMailSender mailSender;

    @Value("${kobit.mail.from}")
    private String noReplyMail;

    @Value("${kobit.mail.report-mail-receivers}")
    private String[] reportMailReceivers;

    public void sendViewCounterReport(List<ViewCounter> counters, YearMonth reportMonth) throws MessagingException {
        String monthLabel = capitalize(reportMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.GERMAN)));
        NumberFormat nf = NumberFormat.getIntegerInstance(Locale.GERMAN);

        StringBuilder rows = new StringBuilder();
        if (counters != null && !counters.isEmpty()) {
            counters.stream()
                    .sorted(Comparator.comparing(vc -> String.valueOf(vc.getCategory())))
                    .forEach(vc -> {
                        String cat = vc.getCategory() == null ? "-" : escapeHtml(vc.getCategory().getDescription());
                        String val = nf.format(vc.getValue() == null ? 0L : vc.getValue());
                        rows.append("<tr>")
                                .append("<td style='padding:8px;border:1px solid #e5e7eb'>").append(cat).append("</td>")
                                .append("<td style='padding:8px;border:1px solid #e5e7eb;text-align:right'>").append(val).append("</td>")
                                .append("</tr>");
                    });
        } else {
            rows.append("<tr><td colspan='2' style='padding:8px;border:1px solid #e5e7eb;text-align:center;color:#6b7280'>Keine Daten</td></tr>");
        }

        String html =
                "<div style=\"font-family:system-ui,Segoe UI,Roboto,Arial,sans-serif;font-size:14px;color:#111827\">"
                        + "<h3 style=\"margin:0 0 8px 0\">UFO-Aufrufzahlen - Report</h3>"
                        + "<p style=\"margin:0 0 12px 0;color:#374151\">Berichtsmonat: <strong>" + monthLabel + "</strong></p>"
                        + "<table style=\"width:100%;border-collapse:collapse;border:1px solid #e5e7eb\">"
                        + "<thead><tr style=\"background:#f9fafb\">"
                        + "<th align=\"left\"  style=\"padding:8px;border:1px solid #e5e7eb;text-transform:uppercase;font-size:12px;color:#6b7280\">Kategorie</th>"
                        + "<th align=\"right\" style=\"padding:8px;border:1px solid #e5e7eb;text-transform:uppercase;font-size:12px;color:#6b7280\">Aufrufe</th>"
                        + "</tr></thead><tbody>" + rows + "</tbody></table></div>";

        MimeMessage msg = mailSender.createMimeMessage();
        MimeMessageHelper h = new MimeMessageHelper(msg, false, StandardCharsets.UTF_8.name());
        h.setFrom(noReplyMail);
        h.setTo(reportMailReceivers);
        h.setSubject("UFO-Aufrufzahlen – " + monthLabel);
        h.setText(html, true);

        mailSender.send(msg);
    }
}
