package de.muenchen.kobit.backend.email.service;

import de.muenchen.kobit.backend.email.model.Email;
import de.muenchen.kobit.backend.email.model.SenderMailAddress;
import de.muenchen.kobit.backend.user.service.UserDataResolver;
import java.util.List;
import java.util.Objects;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final UserDataResolver userDataResolver;

    @Value("${kobit.mail.from}")
    private String noReplyMail;

    public EmailService(JavaMailSender mailSender, UserDataResolver userDataResolver) {
        this.mailSender = mailSender;
        this.userDataResolver = userDataResolver;
    }

    public SenderMailAddress getMailOfUser() {
        return new SenderMailAddress(userDataResolver.getCurrentUser().getEmail());
    }

    public void sendEmail(Email email) throws SendFailedException {

        setSenderMailFromUserToken(email);

        if (!isReleasedFromConfidentialityValid(email)) {
            throw new SendFailedException(
                    "Must be released from confidentiality to send to multiple recipients");
        }
        if (!isReceiverValid(email.getTo())) {
            throw new SendFailedException("There must be at least one receiver!");
        }
        for (String receiver : email.getTo()) {
            try {
                MimeMessage msg = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(msg, false, "UTF-8");

                helper.setFrom(email.getFrom());
                helper.setTo(receiver);
                helper.setSubject(email.getSubject());
                helper.setText(email.getMessage(), true);

                mailSender.send(msg);
            } catch (MessagingException e) {
                log.error("Error while sending mail!", e);
                throw new SendFailedException("Message could not be send!");
            }
        }

        sendEmailCopy(email);
    }

    private void sendEmailCopy(Email email) throws SendFailedException {
        try {

            MimeMessage msgCopy = mailSender.createMimeMessage();
            MimeMessageHelper helperCopy = new MimeMessageHelper(msgCopy, false, "UTF-8");

            helperCopy.setFrom(noReplyMail);
            helperCopy.setTo(email.getFrom());
            helperCopy.setSubject("KoBIT Kopie: " + email.getSubject());
            helperCopy.setText(getMailTxext(email), true);

            mailSender.send(msgCopy);
        } catch (MessagingException e) {
            log.error("Error while sending mail!", e);
            throw new SendFailedException("Message could not be send!");
        }
    }

    private static String getMailTxext(Email email) {
        return "Bitte antworten Sie nicht auf diese E-Mail. Ihre Kontaktanfrage wurde"
                + " empfangen und ist in Bearbeitung. Dies ist eine Kopie der von Ihnen"
                + " gesendeten E-Mail: <br /><br /><pre>Von: "
                + email.getFrom()
                + "<br />"
                + "An: "
                + email.getTo()
                + "<br />"
                + "Betreff: "
                + email.getSubject()
                + "<br /><br />"
                + "Message: "
                + email.getMessage()
                + "</pre>";
    }

    private void setSenderMailFromUserToken(Email email) {
        email.setFrom(getMailOfUser().getEmailAddress());
    }

    private boolean isReceiverValid(List<String> to) {
        return (Objects.nonNull(to) && to.size() > 0);
    }

    private boolean isReleasedFromConfidentialityValid(Email email) {
        return email.getTo().size() <= 1 || email.isReleasedFromConfidentiality();
    }
}
