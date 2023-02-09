package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.responsedtos.GetAuditRecordsResponseDto;
import com.vcriate.vcriateassignment.exceptions.InvalidUser;
import com.vcriate.vcriateassignment.exceptions.UnauthorizeRequest;
import com.vcriate.vcriateassignment.models.Account;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.AccountRepository;
import com.vcriate.vcriateassignment.services.AuditRecordService;
import com.vcriate.vcriateassignment.services.RequestAuthenticationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuditRecordController {
    private AuditRecordService auditRecordService;
    private RequestAuthenticationService requestAuthenticationService;
    public AuditRecordController (AuditRecordService auditRecordService,
                                  RequestAuthenticationService requestAuthenticationService)    {
        this.auditRecordService = auditRecordService;
        this.requestAuthenticationService = requestAuthenticationService;
    }

//    @PreAuthorize("hasAuthority('USER')")
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping(value = "/user/{id}/transactions")
    public GetAuditRecordsResponseDto auditRecordList (@PathVariable String id) throws Exception    {

        if(!requestAuthenticationService.isRequestAuthentic(id))    {
            throw new UnauthorizeRequest("user id do not match with path variable");
        }

        List<AuditRecord> auditRecordList = auditRecordService.getAuditRecordsOfUser(Long.parseLong(id));

        GetAuditRecordsResponseDto getAuditRecordsResponseDto = new GetAuditRecordsResponseDto();
        getAuditRecordsResponseDto.setAuditRecordList(auditRecordList);

        if(auditRecordList.isEmpty()) {

            getAuditRecordsResponseDto.setMessage("user has not made any transactions");
        }

        return getAuditRecordsResponseDto;
    }
}
