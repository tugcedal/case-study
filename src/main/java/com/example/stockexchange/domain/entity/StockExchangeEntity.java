package com.example.stockexchange.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Entity
@Table(name = "stock_exchange")
@EqualsAndHashCode(exclude = "stocks")
public class StockExchangeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "live_in_market")
    private boolean liveInMarket;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("stockExchanges")
    @JoinTable(
            name = "stock_exchange_stock",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "stocks.id")
    )
    private Set<StockEntity> stocks;
}
