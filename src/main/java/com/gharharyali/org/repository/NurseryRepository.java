package com.gharharyali.org.repository;

import com.gharharyali.org.domain.Nursery;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Nursery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NurseryRepository extends JpaRepository<Nursery, Long>, JpaSpecificationExecutor<Nursery> {
}
