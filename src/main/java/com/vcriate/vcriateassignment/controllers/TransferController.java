package com.vcriate.vcriateassignment.controllers;

import com.vcriate.vcriateassignment.dtos.requestdtos.CreateTransferRequestDto;
import com.vcriate.vcriateassignment.dtos.responsedtos.CreateTransferResponseDto;
import com.vcriate.vcriateassignment.models.Transaction;
import com.vcriate.vcriateassignment.services.CreateTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {
    private CreateTransferService createTransferService;
    @Autowired
    public TransferController (CreateTransferService createTransferService)    {
        this.createTransferService = createTransferService;
    }

    @PostMapping(value = "/transfer/{id}")
    public CreateTransferResponseDto createTransfer (CreateTransferRequestDto requestDto, @PathVariable String id)  {
        Transaction transaction = createTransferService.createTransfer(requestDto.getTransferToUserId(),
                requestDto.getAmount(),
                Long.parseLong(id));

        CreateTransferResponseDto createTransferResponseDto = new CreateTransferResponseDto();
        createTransferResponseDto.setTransaction(transaction);
        createTransferResponseDto.setMessage("SUCCESS");
        return createTransferResponseDto;
    }
}
