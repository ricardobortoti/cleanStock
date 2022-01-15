package com.bortoti.cleanstock;

import com.bortoti.cleanstock.adapter.out.persistence.BrokeragePersistenceAdapter;
import com.bortoti.cleanstock.adapter.out.persistence.entity.AssetJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.entity.OperationJpaEntity;
import com.bortoti.cleanstock.adapter.out.persistence.repository.AssetJpaRepository;
import com.bortoti.cleanstock.adapter.out.persistence.repository.OperationJpaRepository;
import com.bortoti.cleanstock.application.domain.Brokerage;
import com.bortoti.cleanstock.application.domain.BrokerageItem;
import com.bortoti.cleanstock.application.domain.enums.OperationType;
import org.apache.catalina.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class CleanStockApplication {
    /*@Autowired
    AssetJpaRepository assetJpaRepository;
    @Autowired
    BrokeragePersistenceAdapter brokeragePersistenceAdapter;*/

    public static void main(String[] args) {
        SpringApplication.run(CleanStockApplication.class, args);
    }

/*
    @Override
    @Transactional
    public void run(String... args) throws Exception {

        AssetJpaEntity assetJpaEntity = new AssetJpaEntity(UUID.randomUUID(), "TICK3");
        assetJpaRepository.save(assetJpaEntity);

        List<BrokerageItem> brokerageItems = new ArrayList<>();

        brokerageItems.add(new BrokerageItem("TICK3",
                BigDecimal.valueOf(10),
                BigDecimal.valueOf(10),
                OperationType.BUY ));

        Brokerage brokerage = new Brokerage(BigDecimal.valueOf(10),
                LocalDate.now(),
                brokerageItems);

        brokeragePersistenceAdapter.saveBrokerage(brokerage);

        var asset = assetJpaRepository.findAll();

    }
 */
}
