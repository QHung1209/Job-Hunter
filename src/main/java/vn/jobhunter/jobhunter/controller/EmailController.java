package vn.jobhunter.jobhunter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.jobhunter.jobhunter.service.EmailService;
import vn.jobhunter.jobhunter.service.SubscriberService;

@RestController
public class EmailController {
    private final EmailService emailService;
    private final SubscriberService subscriberService;

    public EmailController(EmailService emailService, SubscriberService subscriberService) {
        this.emailService = emailService;
        this.subscriberService = subscriberService;

    }

    @GetMapping("/email")
    public String SendEmail() {
        this.subscriberService.sendSubscribersEmailJobs();
        return "OK";
    }
}
