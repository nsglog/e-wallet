package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateDepositRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateDepositResponseDto;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.services.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {
    private DepositService depositService;
    @Autowired
    public DepositController (DepositService depositService)    {
        this.depositService = depositService;
    }

    @PostMapping(value = "/user/{id}/deposit")
    public CreateDepositResponseDto createDeposit (@RequestBody CreateDepositRequestDto requestDto, @PathVariable String id)  {

        AuditRecord auditRecord;
        CreateDepositResponseDto createDepositResponseDto = new CreateDepositResponseDto();

        try {
            auditRecord = depositService.createDeposit(requestDto.getAmount(), Long.parseLong(id));
            createDepositResponseDto.setMessage("SUCCESS");
            createDepositResponseDto.setAuditRecord(auditRecord);
        }
        catch (Exception exception) {
            createDepositResponseDto.setMessage(exception.getMessage());
        }

        return createDepositResponseDto;
    }
}
