package com.eventdao.api.dto.response.event;

import com.eventdao.api.entity.event.EventPollAnswer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventPollQuestionResponse {

    private Long questionId;
    private String question;
    private List<EventPollAnswer> answers;
}
