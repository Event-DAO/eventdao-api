package com.eventdao.api.service.event;

import com.eventdao.api.dto.request.event.EventRequest;
import com.eventdao.api.dto.response.event.EventLocationResponse;
import com.eventdao.api.dto.response.event.ImageResponse;
import com.eventdao.api.dto.response.event.EventListResponse;
import com.eventdao.api.dto.response.event.EventResponse;
import com.eventdao.api.entity.event.Event;
import com.eventdao.api.entity.event.EventLocation;
import com.eventdao.api.exception.EventDaoException;
import com.eventdao.api.exception.EventLocationNotFoundException;
import com.eventdao.api.repository.event.EventLocationRepository;
import com.eventdao.api.repository.event.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventLocationRepository eventLocationRepository;

    public EventListResponse getEventDetails() throws EventDaoException {

        List<Event> eventList = (List<Event>) eventRepository.findAll();
        EventListResponse eventListResponse = new EventListResponse();

        LocalDateTime now = LocalDateTime.now();
        eventList.forEach(event-> {
            EventResponse eventResponse = new EventResponse();
            BeanUtils.copyProperties(event,eventResponse);
            setImages(event, eventResponse);
            setEventLocation(event, eventResponse);
            if (event.getEventStartDate().isAfter(now)) {
                eventListResponse.getUpcomingEvents().add(eventResponse);
            } else if(event.getEventEndDate().isAfter(now) && event.isActive()) {
                eventListResponse.getActiveEvents().add(eventResponse);
            } else {
                eventListResponse.getPastEvents().add(eventResponse);
            }
        });

        return eventListResponse;
    }

    private void setEventLocation(Event event, EventResponse eventResponse) throws EventDaoException {
        Optional<EventLocation> eventLocationOpt = eventLocationRepository.findById(event.getEventLocationId());
        if (eventLocationOpt.isEmpty()) {
            throw new EventLocationNotFoundException();
        }
        EventLocationResponse eventLocationResponse = new EventLocationResponse();
        EventLocation eventLocation = eventLocationOpt.get();
        BeanUtils.copyProperties(eventLocation,eventLocationResponse);
        ImageResponse imageResponse = ImageResponse.builder()
                                                .smallImageUrl(eventLocation.getSmallImg())
                                                .midImageUrl(eventLocation.getMidImg())
                                                .bigImageUrl(eventLocation.getBigImg()).build();
        eventLocationResponse.setImages(imageResponse);
        eventResponse.setEventLocation(eventLocationResponse);
    }

    private void setImages(Event event, EventResponse eventResponse) {
        ImageResponse images = new ImageResponse();
        images.setBigImageUrl(event.getBigImgUrl());
        images.setMidImageUrl(event.getMidImgUrl());
        images.setSmallImageUrl(event.getSmallImgUrl());
        eventResponse.setImages(images);
    }

    public EventResponse saveEvent(EventRequest eventRequest) {
        Event event = new Event();
        BeanUtils.copyProperties(eventRequest,event);
        Event savedEvent = eventRepository.save(event);
        EventResponse eventResponse = new EventResponse();
        BeanUtils.copyProperties(savedEvent,eventResponse);
        return eventResponse;
    }
}
