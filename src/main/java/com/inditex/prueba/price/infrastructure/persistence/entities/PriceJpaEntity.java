package com.inditex.prueba.price.infrastructure.persistence.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PriceJpaEntity.TABLE_NAME)
public class PriceJpaEntity {

    public static final String TABLE_NAME = "PRICES";

    public static final String BRAND_ID_COL = "BRAND_ID";
    public static final String START_DATE_COL = "START_DATE";
    public static final String END_DATE_COL = "END_DATE";
    public static final String PRICE_LIST_COL = "PRICE_LIST";
    public static final String PRODUCT_ID_COL = "PRODUCT_ID";
    public static final String PRIORITY_COL = "PRIORITY";
    public static final String PRICE_COL = "PRICE";
    public static final String CURR_COL = "CURR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = BRAND_ID_COL, nullable = false)
    private Integer brandId;

    @Column(name = START_DATE_COL, nullable = false)
    private LocalDateTime startDate;

    @Column(name = END_DATE_COL, nullable = false)
    private LocalDateTime endDate;

    @Column(name = PRICE_LIST_COL, nullable = false)
    private Integer priceList;

    @Column(name = PRODUCT_ID_COL, nullable = false)
    private Integer productId;

    @Column(name = PRIORITY_COL, nullable = false)
    private Integer priority;

    @Column(name = PRICE_COL, nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = CURR_COL, nullable = false, length = 3)
    private String currency;
}

