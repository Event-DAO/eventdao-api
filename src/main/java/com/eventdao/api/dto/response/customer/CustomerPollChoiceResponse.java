package com.eventdao.api.dto.response.customer;

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
public class CustomerPollChoiceResponse {

    List<CustomerPollChoice> customerPollChoices;
}
