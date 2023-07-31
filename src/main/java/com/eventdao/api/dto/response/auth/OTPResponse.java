package com.eventdao.api.dto.response.auth;

import com.eventdao.api.entity.constant.OTPEnum;
import lombok.Data;

import java.util.List;

@Data
public class OTPResponse {
    List<OTPEnum> otpList;

    public OTPResponse(List<OTPEnum> otpList) {
        this.otpList = otpList;
    }
}
