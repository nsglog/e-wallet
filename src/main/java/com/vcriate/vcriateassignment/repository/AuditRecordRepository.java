package com.vcriate.vcriateassignment.repository;

import com.vcriate.vcriateassignment.models.AuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditRecordRepository extends JpaRepository<AuditRecord, Long> {

    AuditRecord save ( AuditRecord auditRecord);
    @Query(value = "select * from audit_record where wallet_id = ?1", nativeQuery = true)
    List<AuditRecord> findAuditRecords (Long id);

}
