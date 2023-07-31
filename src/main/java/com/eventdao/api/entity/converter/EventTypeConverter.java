package com.eventdao.api.entity.converter;

import com.eventdao.api.entity.constant.EventType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class EventTypeConverter implements AttributeConverter<EventType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(EventType eventType) {
		return eventType.getId();
	}

	@Override
	public EventType convertToEntityAttribute(Integer code) {
		return EventType.valueOf(code);
	}


}