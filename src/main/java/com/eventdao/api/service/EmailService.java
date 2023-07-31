package com.eventdao.api.service;

import com.eventdao.api.entity.customer.Customer;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    private final static int tokenExpirePeriod = 60 * 5;

    private void sendEmail(String to, String subject, String message) {
        //TODO : send email...
    }

    public void sendOtp(Customer customer, String code) {
        String subject = "Your verification code from EventDao";
        String message = "Hello ,\n\n" +
                "Your EventDao verification code is " + code + ".\n\n" +
                "This code will expire at " +
                DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneOffset.UTC) .format(Instant.now().plusSeconds(tokenExpirePeriod)) + ".";
        sendEmail(customer.getEmail(), subject, message);
    }
}
