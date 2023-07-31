package com.eventdao.api.entity.constant;

import lombok.AllArgsConstructor;

import java.util.stream.Stream;

@AllArgsConstructor
public enum AssetType implements IdentifiableEnum{

    NFT(1),FIAT(2),CRYPTO(3);

    private int id;

    @Override
    public int getId() {
        return id;
    }

    public static AssetType valueOf(Integer id) {
        return Stream.of(AssetType.values()).filter(n->n.getId() == id).findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
