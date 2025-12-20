package com.knowledge.library.repository;

import com.knowledge.library.domain.Knowledge;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long> {
    @Query("SELECT k FROM Knowledge k WHERE k.knowledgeType = :type")
    Page<Knowledge> findByKnowledgeType(@Param("type") String type, Pageable pageable);
}
