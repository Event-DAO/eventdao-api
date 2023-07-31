package com.eventdao.api.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;


@Converter
public class PasswordEncryptConverter implements AttributeConverter<String, String> {
	
	@Value("${jasypt.encryptor.password}")
	private String password;
	
	@Override
	public String convertToDatabaseColumn(String attribute) {
	    StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);
		return encryptor.encrypt(attribute);
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(password);
        return decryptor.decrypt(dbData);
	}

}
