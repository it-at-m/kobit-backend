package de.muenchen.kobit.backend.email.api;

import de.muenchen.kobit.backend.email.model.Email;
import de.muenchen.kobit.backend.email.model.SenderMailAddress;
import de.muenchen.kobit.backend.email.service.EmailService;
import javax.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class EmailController {

    @Autowired private final EmailService emailService;

    EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping(path = "/email")
    public SenderMailAddress getUserEMail() {
        return emailService.getMailOfUser();
    }

    @PostMapping(path = "/email")
    public void sendEmail(@RequestBody Email email) throws MessagingException {
        emailService.sendEmail(email);
    }
}
