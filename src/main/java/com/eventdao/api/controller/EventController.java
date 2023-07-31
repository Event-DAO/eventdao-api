package com.eventdao.api.controller;

import com.eventdao.api.dto.request.customer.CustomerPollChoiceRequest;
import com.eventdao.api.dto.request.event.EventRequest;
import com.eventdao.api.dto.response.customer.CustomerPollChoiceResponse;
import com.eventdao.api.dto.response.event.EventListResponse;
import com.eventdao.api.dto.response.event.EventPollResponse;
import com.eventdao.api.dto.response.event.EventResponse;
import com.eventdao.api.entity.customer.Customer;
import com.eventdao.api.exception.EventDaoException;
import com.eventdao.api.service.event.EventPollService;
import com.eventdao.api.service.event.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventPollService eventPollService;

    @GetMapping("/detail")
    public ResponseEntity<EventListResponse> getEventDetails() throws EventDaoException {
        EventListResponse eventDetails = eventService.getEventDetails();
        return ResponseEntity.ok(eventDetails);
    }

    @PostMapping("/save")
    public ResponseEntity<EventResponse> getEventDetails(@Valid @RequestBody EventRequest eventRequest) throws EventDaoException {
        EventResponse event = eventService.saveEvent(eventRequest);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/get-poll")
    public ResponseEntity<EventPollResponse> getEventDetails(@RequestParam Long eventId) throws EventDaoException {
        EventPollResponse eventPoll = eventPollService.getEventPoll(eventId);
        return ResponseEntity.ok(eventPoll);
    }

    @PostMapping("/save-poll")
    public ResponseEntity<CustomerPollChoiceResponse> saveCustomerPollChoices(@Valid @RequestBody CustomerPollChoiceRequest customerPollChoiceRequest, Customer customer) {
        CustomerPollChoiceResponse customerPollChoiceResponse = eventPollService.saveCustomerPollChoice(customerPollChoiceRequest,customer);
        return ResponseEntity.ok(customerPollChoiceResponse);
    }
}
