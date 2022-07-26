package com.hae.ecs.domain.charging.repository;

import com.hae.ecs.domain.charging.domain.Evse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Evse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvseRepository extends JpaRepository<Evse, Long> {}
