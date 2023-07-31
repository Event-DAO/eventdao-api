package com.eventdao.api.repository.event;

import com.eventdao.api.entity.event.EventPoll;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface EventPollRepository extends CrudRepository<EventPoll, Long> {

    Optional<EventPoll> findByEventId(Long id);
}
