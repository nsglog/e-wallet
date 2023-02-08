package com.vcriate.vcriateassignment.dtos.responsedtos;

import com.vcriate.vcriateassignment.models.AuditRecord;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class GetAuditRecordsResponseDto {
    private List<AuditRecord> auditRecordList;
    private String message;
}
