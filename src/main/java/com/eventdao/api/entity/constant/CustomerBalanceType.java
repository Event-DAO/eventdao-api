package com.eventdao.api.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum CustomerBalanceType implements IdentifiableEnum {

    TRANSFER(1),
    TRANSFER_FEE(2),
    TRANSFER_HOLD(5),
    REVERSE_TRANSFER_HOLD(6),
    TRANSFER_FEE_HOLD(7),
    REVERSE_TRANSFER_FEE_HOLD(8),
    TAX(9);

    private final int id;

    public static CustomerBalanceType getReverse(CustomerBalanceType recordType) {
        switch (recordType) {
            case TRANSFER_HOLD:
                return REVERSE_TRANSFER_FEE_HOLD;
            case TRANSFER_FEE_HOLD:
                return REVERSE_TRANSFER_FEE_HOLD;
            default:
                throw new IllegalArgumentException("Not a valid record type " + recordType);
        }
    }

    public static CustomerBalanceType valueOf(Integer id) {
        return Stream.of(CustomerBalanceType.values()).filter(n->n.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
