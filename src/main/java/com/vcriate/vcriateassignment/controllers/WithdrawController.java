package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateWithdrawRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateWithdrawResponseDto;
import com.vcriate.vcriateassignment.models.Transaction;
import com.vcriate.vcriateassignment.services.CreateWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WithdrawController {
    private CreateWithdrawService createWithdrawService;
    @Autowired
    public WithdrawController (CreateWithdrawService createWithdrawService)    {
        this.createWithdrawService = createWithdrawService;
    }

    @PostMapping(value = "/withdraw/{id}")
    public CreateWithdrawResponseDto createWithdraw (CreateWithdrawRequestDto requestDto, @PathVariable String id)  {
        Transaction transaction = createWithdrawService.createWithdraw(requestDto.getAmount(), Long.parseLong(id));
        CreateWithdrawResponseDto createWithdrawResponseDto = new CreateWithdrawResponseDto();
        createWithdrawResponseDto.setTransaction(transaction);
        createWithdrawResponseDto.setMessage("SUCCESS");
        return createWithdrawResponseDto;
    }
}
