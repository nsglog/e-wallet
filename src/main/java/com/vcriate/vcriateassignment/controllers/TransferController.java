package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateTransferRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateTransferResponseDto;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    private TransferService transferService;
    @Autowired
    public TransferController (TransferService transferService)    {
        this.transferService = transferService;
    }

    @PostMapping(value = "/user/{id}/transfer")
    public CreateTransferResponseDto createTransfer (@RequestBody CreateTransferRequestDto requestDto,
                                                     @PathVariable String id)   {

        AuditRecord auditRecord;
        CreateTransferResponseDto createTransferResponseDto = new CreateTransferResponseDto();

        try {
            auditRecord = transferService.createTransfer(requestDto.getTransferToUserId(),
                    requestDto.getAmount(),
                    Long.parseLong(id));

            createTransferResponseDto.setAuditRecord(auditRecord);
            createTransferResponseDto.setMessage("SUCCESS");
        }
        catch (Exception exception) {
            createTransferResponseDto.setMessage(exception.getMessage());
        }

        return createTransferResponseDto;
    }
}
