package com.eventdao.api.entity.converter;

import com.eventdao.api.entity.constant.CustomerIdentityType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CustomerIdentityTypeConverter implements AttributeConverter<CustomerIdentityType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(CustomerIdentityType identityType) {
		return identityType.getId();
	}

	@Override
	public CustomerIdentityType convertToEntityAttribute(Integer code) {
		return CustomerIdentityType.valueOf(code);
	}
}