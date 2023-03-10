package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateTransferRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateTransferResponseDto;
import com.vcriate.vcriateassignment.exceptions.UnauthorizeRequest;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.services.RequestAuthenticationService;
import com.vcriate.vcriateassignment.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    private TransferService transferService;
    private RequestAuthenticationService requestAuthenticationService;
    @Autowired
    public TransferController (TransferService transferService,
                               RequestAuthenticationService requestAuthenticationService)    {
        this.transferService = transferService;
        this.requestAuthenticationService = requestAuthenticationService;
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping(value = "/user/{id}/transfer")
    public CreateTransferResponseDto createTransfer (@RequestBody CreateTransferRequestDto requestDto,
                                                     @PathVariable String id)   throws Exception{

        if(!requestAuthenticationService.isRequestAuthentic(id))    {
            throw new UnauthorizeRequest("user id do not match with path variable");
        }

        AuditRecord auditRecord;
        CreateTransferResponseDto createTransferResponseDto = new CreateTransferResponseDto();

        try {
            auditRecord = transferService.createTransfer(Long.parseLong(id),
                    requestDto.getAmount(),
                    requestDto.getTransferToUserId());

            createTransferResponseDto.setAuditRecord(auditRecord);
            createTransferResponseDto.setMessage("SUCCESS");
        }
        catch (Exception exception) {
            createTransferResponseDto.setMessage(exception.getMessage());
        }

        return createTransferResponseDto;
    }
}
