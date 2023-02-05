package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateWithdrawRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateWithdrawResponseDto;
import com.vcriate.vcriateassignment.models.AuditRecord;
import com.vcriate.vcriateassignment.services.CreateWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WithdrawController {
    private CreateWithdrawService createWithdrawService;
    @Autowired
    public WithdrawController (CreateWithdrawService createWithdrawService)    {
        this.createWithdrawService = createWithdrawService;
    }

    @PostMapping(value = "/withdraw/{id}")
    public CreateWithdrawResponseDto createWithdraw (@RequestBody CreateWithdrawRequestDto requestDto, @PathVariable String id)  {
        AuditRecord auditRecord = createWithdrawService.createWithdraw(requestDto.getAmount(), Long.parseLong(id));
        CreateWithdrawResponseDto createWithdrawResponseDto = new CreateWithdrawResponseDto();
        createWithdrawResponseDto.setAuditRecord(auditRecord);
        createWithdrawResponseDto.setMessage("SUCCESS");
        return createWithdrawResponseDto;
    }
}
