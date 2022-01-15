package com.bortoti.cleanstock.application.port.out;

import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.adapter.exception.BrokerageNotFoundException;

import java.util.UUID;

public interface LoadBrokeragePort {
    Brokerage loadBrokerage(UUID brokerageId) throws BrokerageNotFoundException;
}
