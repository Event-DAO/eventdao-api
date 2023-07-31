package com.eventdao.api.entity.asset;


import com.eventdao.api.entity.constant.AssetType;
import com.eventdao.api.entity.converter.AssetTypeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Table(name = "asset")
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String description;
    @Column(name = "asset_type_id")
    @Convert(converter = AssetTypeConverter.class)
    private AssetType assetType;
    @CreationTimestamp
    private LocalDateTime created;
}
