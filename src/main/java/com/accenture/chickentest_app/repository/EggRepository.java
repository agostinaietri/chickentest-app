package com.accenture.chickentest_app.repository;

import com.accenture.chickentest_app.model.Egg;
import com.accenture.chickentest_app.model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EggRepository extends JpaRepository<Egg, Long> {
    Egg findAllById(Long l);
}
