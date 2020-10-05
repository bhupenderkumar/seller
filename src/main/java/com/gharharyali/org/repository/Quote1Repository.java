package com.gharharyali.org.repository;

import com.gharharyali.org.domain.Quote1;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Quote1 entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Quote1Repository extends JpaRepository<Quote1, Long>, JpaSpecificationExecutor<Quote1> {
}
