package com.eventdao.api.dto.response.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventPollResponse {

    private Long id;
    private String description;
    private List<EventPollQuestionResponse> question;
}
