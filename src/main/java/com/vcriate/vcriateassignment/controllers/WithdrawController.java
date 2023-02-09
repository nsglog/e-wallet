package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateWithdrawRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateWithdrawResponseDto;
import com.vcriate.vcriateassignment.exceptions.UnauthorizeRequest;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.services.RequestAuthenticationService;
import com.vcriate.vcriateassignment.services.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WithdrawController {
    private WithdrawService withdrawService;
    private RequestAuthenticationService requestAuthenticationService;
    @Autowired
    public WithdrawController (WithdrawService withdrawService,
                               RequestAuthenticationService requestAuthenticationService)    {
        this.withdrawService = withdrawService;
        this.requestAuthenticationService = requestAuthenticationService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/user/{id}/withdraw")
    public CreateWithdrawResponseDto createWithdraw (@RequestBody CreateWithdrawRequestDto requestDto,
                                                     @PathVariable String id)  throws Exception {

        if(!requestAuthenticationService.isRequestAuthentic(id))    {
            throw new UnauthorizeRequest("user id do not match with path variable");
        }

        AuditRecord auditRecord;
        CreateWithdrawResponseDto createWithdrawResponseDto = new CreateWithdrawResponseDto();

        try {
            auditRecord = withdrawService.createWithdraw(requestDto.getAmount(), Long.parseLong(id));
            createWithdrawResponseDto.setAuditRecord(auditRecord);
            createWithdrawResponseDto.setMessage("SUCCESS");
        }
        catch (Exception exception) {
            createWithdrawResponseDto.setMessage(exception.getMessage());
        }

        return createWithdrawResponseDto;
    }
}
