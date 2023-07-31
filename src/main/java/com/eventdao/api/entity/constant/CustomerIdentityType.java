package com.eventdao.api.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum CustomerIdentityType {

	UNDEFINED(0),
	NATIONAL_ID(1),
	DRIVERS_LICENSE(2),
	PASSPORT(3),
	FOREIGN_ID(4),
	TAX_ID(5);
	
	private final Integer id;
	
    public static CustomerIdentityType valueOf(Integer id) {
    	return Stream.of(CustomerIdentityType.values()).filter(n->n.getId().intValue() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
