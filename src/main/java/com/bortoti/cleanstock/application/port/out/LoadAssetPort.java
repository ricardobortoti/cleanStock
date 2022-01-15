package com.bortoti.cleanstock.application.port.out;

import com.bortoti.cleanstock.application.domain.Asset;
import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;

public interface LoadAssetPort {
    Asset loadAsset(String ticker) throws AssetNotFoundException;
}
