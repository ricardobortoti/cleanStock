package com.bortoti.cleanstock.application.service;

import com.bortoti.cleanstock.application.domain.Asset;
import com.bortoti.cleanstock.application.domain.Operation;
import com.bortoti.cleanstock.application.port.in.command.SendOperationCommand;
import com.bortoti.cleanstock.application.port.in.SendBuyOperationUseCase;
import com.bortoti.cleanstock.application.port.out.LoadAssetPort;
import com.bortoti.cleanstock.application.port.out.SaveOperationPort;
import com.bortoti.cleanstock.application.service.exception.AssetNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SendOperationService implements SendBuyOperationUseCase {

    private final LoadAssetPort loadAssetPort;
    private final SaveOperationPort saveOperationPort;

    @Override
    public boolean sendBuyOperation(SendOperationCommand command) throws AssetNotFoundException {
        Asset asset = loadAssetPort.loadAsset(command.getTicker());
        Operation operation = Operation.createBuy(asset, command.getQuantity(), command.getTotalValue(), command.getDate());
        saveOperationPort.saveOperation(operation);
        return true;
    }
}
