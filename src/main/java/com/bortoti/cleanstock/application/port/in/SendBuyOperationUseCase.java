package com.bortoti.cleanstock.application.port.in;

import com.bortoti.cleanstock.application.port.in.command.SendOperationCommand;

public interface SendBuyOperationUseCase {
    boolean sendBuyOperation(SendOperationCommand command) throws Exception;
}