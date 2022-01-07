package com.bortoti.cleanstock.application.port.out;

import com.bortoti.cleanstock.application.domain.Asset;
import com.bortoti.cleanstock.application.service.exception.AssetNotFoundException;

import java.util.Optional;

public interface LoadAssetPort {
    Asset loadAsset(String ticker) throws AssetNotFoundException;
}
