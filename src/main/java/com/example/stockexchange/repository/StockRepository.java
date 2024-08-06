package com.example.stockexchange.repository;

import com.example.stockexchange.domain.entity.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, Long> {
    Optional<StockEntity> findByName(String name);

    void deleteByName(String name);
}