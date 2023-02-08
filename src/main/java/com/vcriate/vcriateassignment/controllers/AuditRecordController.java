package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.responsedtos.GetAuditRecordsResponseDto;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.services.AuditRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuditRecordController {
    private AuditRecordService auditRecordService;
    public AuditRecordController (AuditRecordService auditRecordService)    {
        this.auditRecordService = auditRecordService;
    }

    @GetMapping(value = "/user/{id}/transactions")
    public GetAuditRecordsResponseDto auditRecordList (@PathVariable String id) {

        List<AuditRecord> auditRecordList = auditRecordService.getAuditRecordsOfUser(Long.parseLong(id));

        GetAuditRecordsResponseDto getAuditRecordsResponseDto = new GetAuditRecordsResponseDto();
        getAuditRecordsResponseDto.setAuditRecordList(auditRecordList);

        if(auditRecordList.isEmpty()) {

            getAuditRecordsResponseDto.setMessage("user has not made any transactions");
        }

        return getAuditRecordsResponseDto;
    }
}
