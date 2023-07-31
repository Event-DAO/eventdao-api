package com.eventdao.api.repository.event;

import com.eventdao.api.entity.event.Event;
import org.springframework.data.repository.CrudRepository;


public interface EventRepository extends CrudRepository<Event, Long> {


}
