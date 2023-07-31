package com.eventdao.api.service.event;

import com.eventdao.api.dto.request.customer.CustomerPollChoiceRequest;
import com.eventdao.api.dto.response.customer.CustomerPollChoiceResponse;
import com.eventdao.api.dto.response.event.EventPollQuestionResponse;
import com.eventdao.api.dto.response.event.EventPollResponse;
import com.eventdao.api.entity.customer.Customer;
import com.eventdao.api.entity.customer.CustomerPollChoice;
import com.eventdao.api.entity.event.EventPoll;
import com.eventdao.api.entity.event.EventPollAnswer;
import com.eventdao.api.exception.EventNotFoundException;
import com.eventdao.api.repository.customer.CustomerPollChoiceRepository;
import com.eventdao.api.repository.event.EventPollAnswerRepository;
import com.eventdao.api.repository.event.EventPollQuestionRepository;
import com.eventdao.api.repository.event.EventPollRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventPollService {

    private final EventPollRepository eventPollRepository;
    private final EventPollAnswerRepository eventPollAnswerRepository;
    private final EventPollQuestionRepository eventPollQuestionRepository;
    private final CustomerPollChoiceRepository customerPollChoiceRepository;

    public EventPollResponse getEventPoll(Long eventId) throws EventNotFoundException {
        Optional<EventPoll> eventPollOpt = eventPollRepository.findByEventId(eventId);
        if (eventPollOpt.isEmpty()) {
            throw new EventNotFoundException();
        }
        EventPoll eventPoll = eventPollOpt.get();

        List<EventPollQuestionResponse> eventPollQuestionResponses = new ArrayList<>();

        eventPollQuestionRepository.findAllByEventPollId(eventPoll.getId()).forEach(n-> {
            List<EventPollAnswer> eventPollAnswers = eventPollAnswerRepository.findAllByPollQuestionId(n.getId());

            EventPollQuestionResponse questionResponse= EventPollQuestionResponse.builder()
                    .questionId(n.getId())
                    .question(n.getQuestion())
                    .answers(eventPollAnswers).build();

            eventPollQuestionResponses.add(questionResponse);
        });

        return EventPollResponse.builder()
                .id(eventPoll.getId())
                .description(eventPoll.getDescription())
                .question(eventPollQuestionResponses)
                .build();
    }

    public CustomerPollChoiceResponse saveCustomerPollChoice(CustomerPollChoiceRequest pollChoices,Customer customer) {
        List<CustomerPollChoice> updatedList = pollChoices.getCustomerPollChoices().stream().peek(n -> n.setCustomerId(customer.getId())).collect(Collectors.toList());
        List<CustomerPollChoice> customerPollChoices = (List<CustomerPollChoice>) customerPollChoiceRepository.saveAll(updatedList);

        return CustomerPollChoiceResponse.builder().customerPollChoices(customerPollChoices).build();
    }

}
