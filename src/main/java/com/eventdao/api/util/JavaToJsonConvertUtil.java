package com.eventdao.api.util;

import com.eventdao.api.dto.request.event.EventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JavaToJsonConvertUtil {
	
	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		String writeValueAsString = objMapper.writer().writeValueAsString(new EventRequest());
		System.out.println("Parsed String : " + writeValueAsString);
	}

}
