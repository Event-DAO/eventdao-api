package com.eventdao.api.entity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Role implements IdentifiableEnum {
    ROLE_USER_BASIC(1),
    ROLE_USER_KYC_LEVEL_ONE(2),
    ROLE_USER_KYC_LEVEL_TWO(3),
    ROLE_USER_KYC_LEVEL_THREE(4),
    ROLE_ADMIN(5),
    ROLE_SUPER_ADMIN(6);

    private final int id;

    public static Role valueOf(int id) {
        return Stream.of(Role.values()).filter(n->n.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public String getName() {
        return name();
    }
}
