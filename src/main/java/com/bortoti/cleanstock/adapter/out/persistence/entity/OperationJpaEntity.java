package com.bortoti.cleanstock.adapter.out.persistence.entity;

import com.bortoti.cleanstock.application.domain.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class OperationJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type="uuid-char")
    private UUID id;
    @OneToOne
    private AssetJpaEntity assetJpaEntity;
    private BigDecimal quantity;
    private BigDecimal TotalValue;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private LocalDate date;

}
