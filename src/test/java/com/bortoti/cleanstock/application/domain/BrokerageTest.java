package com.bortoti.cleanstock.application.domain;

import com.bortoti.cleanstock.application.domain.enums.OperationType;
import org.apache.el.stream.Stream;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class BrokerageTest {
    private List<BrokerageItem> brokerageItemList = new ArrayList<>();
    private Brokerage brokerage;

    void whenHaveBrokerageItems() {
        IntStream.range(0, 5)
                .forEach( i -> {
                            brokerageItemList.add(
                                    new BrokerageItem("TEST".formatted(i),
                                            BigDecimal.valueOf(10),
                                            BigDecimal.valueOf(2),
                                            OperationType.BUY)
                            );
                        }
                );
    }

    void whenHaveTestOneItemBuyNote() {
        IntStream.range(0, 1)
                .forEach( i -> {
                            brokerageItemList.add(
                                    new BrokerageItem("PETR4".formatted(i),
                                            BigDecimal.valueOf(5),
                                            BigDecimal.valueOf(20.16),
                                            OperationType.BUY)
                            );
                        }
                );

        brokerage = new Brokerage(BigDecimal.valueOf(101.34),
                LocalDate.now(),
                brokerageItemList);
    }

    @Test
    void testCreationWithItems() {
        whenHaveBrokerageItems();
        Brokerage brokerageWithId = new Brokerage(UUID.randomUUID(),
                BigDecimal.valueOf(100),
                LocalDate.now(),
                brokerageItemList);

        Brokerage brokerageWithoutId = new Brokerage(BigDecimal.valueOf(100.80),
                LocalDate.now(),
                brokerageItemList);

        assertEquals(5, brokerageWithId.getItemCount());
        assertEquals(5, brokerageWithoutId.getItemCount());
    }

    @Test
    void testGetTotalOperationsValueOneItemBuyNote() {
        //when
        whenHaveTestOneItemBuyNote();
        //then
        assertEquals(BigDecimal.valueOf(100.80).setScale(4),
                brokerage.getTotalOperationsValue());
    }

    @Test
    void testGetTotalCostsOneItemBuyNote(){
        //when
        whenHaveTestOneItemBuyNote();
        //then
        assertEquals(BigDecimal.valueOf(0.5400).setScale(4),
                brokerage.getTotalCosts());
    }


}