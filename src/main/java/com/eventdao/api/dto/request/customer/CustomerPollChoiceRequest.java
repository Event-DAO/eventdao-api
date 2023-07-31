package com.eventdao.api.dto.request.customer;

import com.eventdao.api.entity.customer.CustomerPollChoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPollChoiceRequest {

    List<CustomerPollChoice> customerPollChoices;
}
