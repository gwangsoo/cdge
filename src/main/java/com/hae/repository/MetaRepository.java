package com.hae.repository;

import com.hae.domain.Meta;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Meta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MetaRepository extends JpaRepository<Meta, Long> {}
