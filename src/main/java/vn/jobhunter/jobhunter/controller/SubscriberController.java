package vn.jobhunter.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import vn.jobhunter.jobhunter.domain.Subscriber;
import vn.jobhunter.jobhunter.service.SubscriberService;
import vn.jobhunter.jobhunter.util.error.IdInvalidException;

@RestController
public class SubscriberController {
    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @PostMapping("/subscribers")
    public ResponseEntity<Subscriber> createSubscriber(@Valid @RequestBody Subscriber subscriber)
            throws IdInvalidException {
        if (this.subscriberService.isExistSubscriber(subscriber.getName())) {
            throw new IdInvalidException("Ten da ton tai");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.subscriberService.handleCreateSubscriber(subscriber));

    }

    @PutMapping("/subscribers")
    public ResponseEntity<Subscriber> updateSubscriber(@Valid @RequestBody Subscriber subscriber)
            throws IdInvalidException {
        if (this.subscriberService.handleGetSubscriber(subscriber.getId()) == null) {
            throw new IdInvalidException("Id subscriber khong ton tai");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(this.subscriberService.handleUpdateSubscriber(subscriber));

    }

}
