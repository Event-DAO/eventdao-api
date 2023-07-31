package com.eventdao.api.dto.response.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventListResponse {

    private List<EventResponse> activeEvents = new ArrayList<>();
    private List<EventResponse> upcomingEvents  = new ArrayList<>();
    private List<EventResponse> pastEvents  = new ArrayList<>();
}
