package com.accenture.chickentest_app.repository;

import com.accenture.chickentest_app.model.Chicken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChickenRepository extends JpaRepository<Chicken, Long> {
}
