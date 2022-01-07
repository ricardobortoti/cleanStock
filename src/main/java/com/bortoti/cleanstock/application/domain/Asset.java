package com.bortoti.cleanstock.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Asset {
    private UUID id;
    private String ticker;

    public static Asset withId(UUID id, String ticker) {
        return new Asset(id, ticker);
    }

    public static Asset withoutId(String ticker) {
        return new Asset(null, ticker);
    }
}
