package com.bortoti.cleanstock.application.service;

import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import com.bortoti.cleanstock.application.port.in.command.SendOperationCommand;
import com.bortoti.cleanstock.application.service.exception.AssetNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SendOperationServiceIntegrationTest {
    @Autowired
    SendOperationService sendOperationService;
    @Autowired
    AssetJpaRepository assetJpaRepository;

    private String testAsset = "TEST3";

    void whenAssetExists() {
        assetJpaRepository.save(new AssetJpaEntity(UUID.randomUUID(), testAsset));
    }

    @Test
    void sendBuyOperationSuccess() throws AssetNotFoundException {
        //given
        SendOperationCommand sendOperationCommand = new SendOperationCommand(testAsset,
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10),
                LocalDate.now(),
                OperationType.BUY);

        // when
        whenAssetExists();
        boolean res = sendOperationService.sendBuyOperation(sendOperationCommand);
        //then
        assertTrue(res);
    }
}