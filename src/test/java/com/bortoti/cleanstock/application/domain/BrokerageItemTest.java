package com.bortoti.cleanstock.application.domain;

import com.bortoti.cleanstock.application.domain.enums.OperationType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BrokerageItemTest {
    BrokerageItem brokerageItem;

    private void whenItemHasFullValues() {
        brokerageItem = new BrokerageItem("TEST3",
                BigDecimal.valueOf(100),
                BigDecimal.valueOf(10), OperationType.BUY);
    }

    private void whenItemHasDecimalValues() {
        brokerageItem = new BrokerageItem("TEST3",
                BigDecimal.valueOf(97),
                BigDecimal.valueOf(12.34), OperationType.BUY);
    }

    @Test
    void whenItemHasFullValues_getItemValue() {
        //when
        whenItemHasFullValues();
        //then
        assertEquals(BigDecimal.valueOf(1000), brokerageItem.getItemValue());
    }

    @Test
    void whenItemHasDecimalValues_getItemValue() {
        //when
        whenItemHasDecimalValues();
        //then
        assertEquals(BigDecimal.valueOf(1196.98).setScale(2), brokerageItem.getItemValue());
    }
    @Test
    void whenHaveOneItem_getItemValueWithCosts() {
        //when
        whenItemHasFullValues();
        var valueWithCosts= brokerageItem.getItemValueWithCosts(BigDecimal.valueOf(1000),
                BigDecimal.valueOf(10));
        //then
        assertEquals(BigDecimal.valueOf(1010).setScale(10), valueWithCosts);
    }

    @Test
    void whenItemIsHalfTotalOperationsValue_getItemValueWithCosts() {
        //when
        whenItemHasFullValues();
        var valueWithCosts= brokerageItem.getItemValueWithCosts(BigDecimal.valueOf(2000),
                BigDecimal.valueOf(10));
        //then
        assertEquals(BigDecimal.valueOf(1005.0).setScale(10), valueWithCosts);
    }

    @Test
    void whenItemIsOneThirdTotalOperationsValue_getItemValueWithCosts() {
        //when
        whenItemHasFullValues();
        var valueWithCosts= brokerageItem.getItemValueWithCosts(BigDecimal.valueOf(3000),
                BigDecimal.valueOf(10));
        //then
        assertEquals(BigDecimal.valueOf(1003.3333333330).setScale(10), valueWithCosts);
    }
}