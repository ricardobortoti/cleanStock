package com.bortoti.cleanstock.mapper;

import com.bortoti.cleanstock.adapter.out.persistence.entity.BrokerageJpaEntity;
import com.bortoti.cleanstock.application.domain.Brokerage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface BrokerageMapper {
    @Mapping(target = "brokerageItems.brokerageJpaEntity", ignore = true)
    Brokerage toDomain(BrokerageJpaEntity brokerageJpaEntity);
    BrokerageJpaEntity toJpaEntity(Brokerage brokerage);
}
