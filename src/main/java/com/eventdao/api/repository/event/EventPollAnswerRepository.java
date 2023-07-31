package com.eventdao.api.repository.event;

import com.eventdao.api.entity.event.EventPollAnswer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EventPollAnswerRepository extends CrudRepository<EventPollAnswer, Long> {

    List<EventPollAnswer> findAllByPollQuestionId(Long eventPollId);
}
