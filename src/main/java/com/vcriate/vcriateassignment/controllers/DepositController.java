package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateDepositRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateDepositResponseDto;
import com.vcriate.vcriateassignment.exceptions.UnauthorizeRequest;
import com.vcriate.vcriateassignment.models.Account;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.models.Wallet;
import com.vcriate.vcriateassignment.repository.AccountRepository;
import com.vcriate.vcriateassignment.services.DepositService;
import com.vcriate.vcriateassignment.services.RequestAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DepositController {
    private DepositService depositService;
    private RequestAuthenticationService requestAuthenticationService;
    @Autowired
    public DepositController (DepositService depositService,
                              RequestAuthenticationService requestAuthenticationService)    {
        this.depositService = depositService;
        this.requestAuthenticationService = requestAuthenticationService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/user/{id}/deposit")
    public CreateDepositResponseDto createDeposit (@RequestBody CreateDepositRequestDto requestDto,
                                                   @PathVariable String id)  throws Exception   {

        if(!requestAuthenticationService.isRequestAuthentic(id))    {
            throw new UnauthorizeRequest("user id do not match with path variable");
        }

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
