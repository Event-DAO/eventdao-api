package com.eventdao.api.entity.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "event_location")
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class EventLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(length = 1000)
    private String address;
    private String city;
    private String state;
    private String country;
    @Column(length = 5000)
    private String areaDetails;
    private int capacity;
    @Column(length = 5000)
    private String facilities;
    private BigDecimal latitude;
    private BigDecimal longitude;
    @Column(length = 1000)
    private String bigImg;
    @Column(length = 1000)
    private String midImg;
    @Column(length = 1000)
    private String smallImg;
}
