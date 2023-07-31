package com.eventdao.api.entity.constant;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum EventType implements IdentifiableEnum {

    GENERIC(0),
    SPORTS(1),
    CONCERT(2),
    ART(3);

    private int id;

    @Override
    public int getId() {
        return id;
    }

    public static EventType valueOf(Integer id) {
        return Stream.of(EventType.values()).filter(n->n.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
