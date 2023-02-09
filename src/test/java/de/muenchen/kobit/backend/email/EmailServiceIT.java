package de.muenchen.kobit.backend.email;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import de.muenchen.kobit.backend.MicroServiceApplication;
import de.muenchen.kobit.backend.email.model.Email;
import de.muenchen.kobit.backend.email.service.EmailService;
import de.muenchen.kobit.backend.user.service.UserDataResolver;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {MicroServiceApplication.class})
@Slf4j
class EmailServiceIT {

    @RegisterExtension
    static final GreenMailExtension greenMail =
            new GreenMailExtension(ServerSetupTest.SMTP)
                    .withConfiguration(
                            GreenMailConfiguration.aConfig().withDisabledAuthentication())
                    .withPerMethodLifecycle(false);

    @Autowired private JavaMailSender mailSender;

    @Autowired private EmailService emailService;

    @Autowired private UserDataResolver userDataResolver;

    private List<String> receiver;

    @BeforeEach
    void init() {
        emailService = new EmailService(mailSender, userDataResolver);
        receiver = new ArrayList<>();
    }

    @Test
    public void shouldSendMailCorrectToUser() throws MessagingException {
        receiver.add("receiver.kobit@muenchen.de");
        Email mail =
                new Email(
                        "sender.kobit@muenchen.de",
                        receiver,
                        "IT Test Mailversand",
                        "default Message",
                        false);
        try {
            emailService.sendEmail(mail);
        } catch (MessagingException e) {
            log.error("Failed to send message during test!");
        }

        MimeMessage message = greenMail.getReceivedMessages()[0];
        assertEquals(GreenMailUtil.getBody(message), mail.getMessage());
        assertEquals(
                message.getAllRecipients()[0].toString(), mail.getTo().stream().findFirst().get());
    }

    @Test
    public void shouldSendMailCorrectUTF8ToUser() throws MessagingException {
        receiver.add("empfaenger.kobit@muenchen.de");
        Email mail =
                new Email(
                        "sender.kobit@muenchen.de",
                        receiver,
                        "IT Test Gemüse und Spaß",
                        "default Message",
                        false);
        try {
            emailService.sendEmail(mail);
        } catch (MessagingException e) {
            log.error("Failed to send message during test!");
        }

        MimeMessage message = greenMail.getReceivedMessages()[0];
        assertEquals(GreenMailUtil.getBody(message), mail.getMessage());
        assertEquals(
                message.getAllRecipients()[0].toString(), mail.getTo().stream().findFirst().get());
    }

    @Test
    public void shouldSendMailToMultipleUsers() throws MessagingException {
        receiver.add("empfaenger.kobit@muenchen.de");
        receiver.add("receiver.kobit@muenchen.de");
        Email mail =
                new Email(
                        "sender.kobit@muenchen.de",
                        receiver,
                        "IT Test Gemüse und Spaß",
                        "default Message",
                        true);

        try {
            emailService.sendEmail(mail);
        } catch (MessagingException e) {
            log.error("Failed to send message during test!");
        }

        MimeMessage message = greenMail.getReceivedMessages()[0];
        assertEquals(GreenMailUtil.getBody(message), mail.getMessage());

        assertTrue(
                mail.getTo()
                        .containsAll(
                                Arrays.stream(message.getAllRecipients())
                                        .map(Address::toString)
                                        .collect(Collectors.toList())));
    }

    @Test
    public void shouldNotSendMailToUserConfidentiality() throws MessagingException {
        receiver.add("empfaenger.kobit@muenchen.de");
        receiver.add("receiver.kobit@muenchen.de");
        Email mail =
                new Email(
                        "sender.kobit@muenchen.de",
                        receiver,
                        "IT Test Gemüse und Spaß",
                        "default Message",
                        false);

        SendFailedException ex =
                assertThrows(
                        SendFailedException.class,
                        () -> emailService.sendEmail(mail),
                        "Expected sendMail() to throw exception!");

        assertEquals(
                ex.getMessage(),
                "Must be released from confidentiality to send to multiple recipients");
    }

    @Test
    public void shouldNotSendMailToUserNoReceiver() throws MessagingException {
        Email mail =
                new Email(
                        "sender.kobit@muenchen.de",
                        receiver,
                        "IT Test Gemüse und Spaß",
                        "default Message",
                        false);

        SendFailedException ex =
                assertThrows(
                        SendFailedException.class,
                        () -> emailService.sendEmail(mail),
                        "Expected sendMail() to throw exception!");

        assertEquals(
                ex.getMessage(),
                "Must be released from confidentiality to send to multiple recipients");
    }
}
