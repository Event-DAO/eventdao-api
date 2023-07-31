package com.eventdao.api.entity.converter;

import com.eventdao.api.entity.constant.CustomerBalanceType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CustomerBalanceTypeConverter implements AttributeConverter<CustomerBalanceType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(CustomerBalanceType identityType) {
		return identityType.getId();
	}

	@Override
	public CustomerBalanceType convertToEntityAttribute(Integer code) {
		return CustomerBalanceType.valueOf(code);
	}


}