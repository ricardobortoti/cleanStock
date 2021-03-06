package com.bortoti.cleanstock.application.port.out;

import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;
import com.bortoti.cleanstock.application.domain.Brokerage;

public interface SaveBrokeragePort {
    Brokerage saveBrokerage(Brokerage brokerage) throws AssetNotFoundException;
}
