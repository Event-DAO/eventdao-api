package com.eventdao.api.entity.converter;

import com.eventdao.api.entity.constant.AssetType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class AssetTypeConverter implements AttributeConverter<AssetType, Integer> {

	@Override
	public Integer convertToDatabaseColumn(AssetType identityType) {
		return identityType.getId();
	}

	@Override
	public AssetType convertToEntityAttribute(Integer code) {
		return AssetType.valueOf(code);
	}


}