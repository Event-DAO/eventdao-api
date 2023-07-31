package com.eventdao.api.entity.event;

import com.eventdao.api.entity.constant.EventType;
import com.eventdao.api.entity.converter.EventTypeConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "events"/*, uniqueConstraints = { @UniqueConstraint(columnNames = { "event_name", "event_start_date" }) }*/)
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Convert(converter = EventTypeConverter.class)
    @Column(name = "event_type_id")
    private EventType eventType;
    @JsonIgnore
    private Long eventLocationId;
    private String eventName;
    @Column(length = 10000)
    private String shortDescription;
    @Column(length = 10000)
    private String description;
    @Column(length = 1000)
    private String bigImgUrl;
    @Column(length = 1000)
    private String midImgUrl;
    @Column(length = 1000)
    private String smallImgUrl;
    private LocalDateTime eventStartDate;
    private LocalDateTime eventEndDate;
    private boolean isActive;

}
