package com.hae.repository;

import com.hae.domain.Evse;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Evse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvseRepository extends JpaRepository<Evse, Long> {}
