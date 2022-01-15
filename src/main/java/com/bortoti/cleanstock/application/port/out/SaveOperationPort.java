package com.bortoti.cleanstock.application.port.out;

import com.bortoti.cleanstock.adapter.exception.AssetNotFoundException;
import com.bortoti.cleanstock.application.domain.Operation;

public interface SaveOperationPort {
    Operation saveOperation(Operation operation) throws AssetNotFoundException;
}
