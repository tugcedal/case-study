package com.example.stockexchange.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "stock")
@EqualsAndHashCode(exclude = "stockExchanges")
public class StockEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @ToString.Exclude
    @ManyToMany(mappedBy = "stocks", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("stocks")
    private Set<StockExchangeEntity> stockExchanges;
}
