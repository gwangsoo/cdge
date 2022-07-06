package com.hae.repository;

import com.hae.domain.Charger;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Charger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChargerRepository extends JpaRepository<Charger, Long> {}
