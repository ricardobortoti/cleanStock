package com.bortoti.cleanstock.mapper;

import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageItemJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageJpaEntity;
import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.application.domain.BrokerageItem;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BrokerageMapperTest {
    BrokerageMapper brokerageMapper = Mappers.getMapper(BrokerageMapper.class);
    private BrokerageJpaEntity brokerageJpaEntity;
    private Brokerage brokerage;
    private List<BrokerageItemJpaEntity> brokerageItemJpaEntityList = new ArrayList<>();
    private List<BrokerageItem> brokerageItemList = new ArrayList<>();

    void givenExistingBrokerageJpaEntityWith3Items() {
        brokerageJpaEntity = new BrokerageJpaEntity();
        brokerageJpaEntity.setBrokerageId(UUID.randomUUID());
        brokerageJpaEntity.setNetBrokerageValue(BigDecimal.valueOf(1000.20));
        brokerageJpaEntity.setDate(LocalDate.of(2021,01,01));

        IntStream.range(0, 3)
                .forEach( i -> {
                    brokerageItemJpaEntityList.add(
                                    new BrokerageItemJpaEntity(UUID.randomUUID(),
                                            "PETR4",
                                            BigDecimal.valueOf(11.733),
                                            BigDecimal.valueOf(2.76),
                                            OperationType.BUY,
                                            null)
                            );
                        }
                );


        brokerageJpaEntity.setBrokerageItems(brokerageItemJpaEntityList);
    }
    void givenExistingBrokerageEntityWith3Items() {
        IntStream.range(0, 3)
                .forEach(i -> {
                            brokerageItemList.add(
                                    new BrokerageItem(UUID.randomUUID(),
                                            "PETR4",
                                            BigDecimal.valueOf(10),
                                            BigDecimal.valueOf(10.20),
                                            OperationType.BUY
                                    )
                            );
                        }
                );


        brokerage = new Brokerage(UUID.randomUUID(),
                BigDecimal.valueOf(1000.20),
                LocalDate.of(2021, 01, 01),
                brokerageItemList);
    }

    @Test
    void toDomainTest() {
        //given
        givenExistingBrokerageJpaEntityWith3Items();
        //when
        var brokerage = brokerageMapper.toDomain(brokerageJpaEntity);
        //then
        assertEquals(brokerageJpaEntity.getBrokerageId(), brokerage.getBrokerageId());
        assertEquals(brokerageJpaEntity.getNetBrokerageValue(), brokerage.getNetBrokerageValue());
        assertEquals(brokerageJpaEntity.getDate(), brokerage.getDate());

        assertEquals(brokerageJpaEntity.getBrokerageItems().size(), brokerage.getBrokerageItems().stream().count());
        for (var actual:brokerage.getBrokerageItems()) {
            var item = brokerageJpaEntity.getBrokerageItems().stream().filter(f -> f.getBrokerageItemId().equals(actual.getBrokerageItemId())).findFirst();
            assertNotNull(item.get());
            var expected = item.get();

            assertEquals(expected.getBrokerageItemId(), actual.getBrokerageItemId());
            assertEquals(expected.getAssetTicker(), actual.getAssetTicker());
            assertEquals(expected.getPrice(), actual.getPrice());
            assertEquals(expected.getQuantity(), actual.getQuantity());
            assertEquals(expected.getType(), actual.getType());
        }
    }

    @Test
    void toJpaEntityTest() {
        //given
        givenExistingBrokerageEntityWith3Items();
        //when
        var brokerageJpaEntity = brokerageMapper.toJpaEntity(brokerage);
        //then
        assertEquals(brokerage.getBrokerageId(), brokerageJpaEntity.getBrokerageId());
        assertEquals(brokerage.getNetBrokerageValue(), brokerageJpaEntity.getNetBrokerageValue());
        assertEquals(brokerage.getDate(), brokerageJpaEntity.getDate());

        for (var actual:brokerageJpaEntity.getBrokerageItems()) {
            var item = brokerage.getBrokerageItems().stream().filter(f -> f.getBrokerageItemId().equals(actual.getBrokerageItemId())).findFirst();

            assertNotNull(item.get());
            var expected = item.get();

            assertEquals(expected.getBrokerageItemId(), actual.getBrokerageItemId());
            assertEquals(expected.getAssetTicker(), actual.getAssetTicker());
            assertEquals(expected.getPrice(), actual.getPrice());
            assertEquals(expected.getQuantity(), actual.getQuantity());
            assertEquals(expected.getType(), actual.getType());
        }
    }
}