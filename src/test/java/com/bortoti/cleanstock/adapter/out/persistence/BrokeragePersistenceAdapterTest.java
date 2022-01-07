package com.bortoti.cleanstock.adapter.out.persistence;

import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageItemJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.BrokerageJpaRepository;
import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.application.domain.BrokerageItem;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import com.bortoti.cleanstock.application.port.out.LoadBrokeragePort;
import com.bortoti.cleanstock.application.port.out.SaveBrokeragePort;
import com.bortoti.cleanstock.application.service.exception.BrokerageNotFoundException;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrokeragePersistenceAdapterTest {

    private Brokerage brokerage;
    private List<BrokerageItem> brokerageItemList = new ArrayList<>();

    @Autowired
    private SaveBrokeragePort saveBrokeragePort;
    @Autowired
    private LoadBrokeragePort loadBrokeragePort;

    void givenNewBrokerageEntityWith3Items() {
        IntStream.range(0, 3)
                .forEach(i -> {
                            brokerageItemList.add(
                                    new BrokerageItem(null,
                                            "PETR4",
                                            BigDecimal.valueOf(10),
                                            BigDecimal.valueOf(10.20),
                                            OperationType.BUY
                                    )
                            );
                        }
                );


        brokerage = new Brokerage(null,
                BigDecimal.valueOf(1000.20),
                LocalDate.of(2021, 01, 01),
                brokerageItemList);
    }



    @Test
    void saveBrokerage() throws BrokerageNotFoundException {
        //given
        givenNewBrokerageEntityWith3Items();
        //when
        var saved = saveBrokeragePort.saveBrokerage(brokerage);
        var result = loadBrokeragePort.loadBrokerage(saved.getBrokerageId());
        //then
        assertNotNull(result.getBrokerageId());
        assertEquals(brokerage.getNetBrokerageValue().setScale(2), result.getNetBrokerageValue());
        assertEquals(brokerage.getDate(), result.getDate());
        assertEquals(brokerage.getTotalCosts(), result.getTotalCosts());
        assertEquals(brokerage.getTotalOperationsValue(), result.getTotalOperationsValue());
        assertEquals(brokerage.getItemCount(), result.getItemCount());
    }
}