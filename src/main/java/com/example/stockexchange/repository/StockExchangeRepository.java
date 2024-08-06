package com.example.stockexchange.repository;

import com.example.stockexchange.domain.entity.StockExchangeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockExchangeRepository extends JpaRepository<StockExchangeEntity, Long> {
    Optional<StockExchangeEntity> findByName(String name);

    Optional<List<StockExchangeEntity>> findByNameIn(List<String> name);

}