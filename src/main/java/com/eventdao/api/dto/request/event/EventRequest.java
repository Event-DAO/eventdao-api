package com.eventdao.api.dto.request.event;

import com.eventdao.api.entity.constant.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    private EventType eventType;
    private String eventName;
    private String shortDescription;
    private String description;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private String eventLocation;
    private String areaDetails;
    private boolean isActive;

}
