package com.gharharyali.org.repository;

import com.gharharyali.org.domain.FeedBack;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FeedBack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeedBackRepository extends JpaRepository<FeedBack, Long>, JpaSpecificationExecutor<FeedBack> {
}
