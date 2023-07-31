package com.eventdao.api.repository.event;

import com.eventdao.api.entity.event.EventPollAnswer;
import com.eventdao.api.entity.event.EventPollQuestion;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EventPollQuestionRepository extends CrudRepository<EventPollQuestion, Long> {

    List<EventPollQuestion> findAllByEventPollId(Long eventPollId);
}
