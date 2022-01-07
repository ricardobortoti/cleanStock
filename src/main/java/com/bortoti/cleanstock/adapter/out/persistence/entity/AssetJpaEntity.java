package com.bortoti.cleanstock.adapter.out.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class AssetJpaEntity {
    @Id
    private UUID id;
    private String ticker;
}
