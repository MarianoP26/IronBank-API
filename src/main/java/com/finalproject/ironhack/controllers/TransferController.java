package com.finalproject.ironhack.controllers;

import com.finalproject.ironhack.DTOs.*;
import com.finalproject.ironhack.controllers.interfaces.TransferControllerInterface;
import com.finalproject.ironhack.models.Transfer;
import com.finalproject.ironhack.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class TransferController implements TransferControllerInterface {


    @Autowired
    TransferService transferService;

    @Override
    @GetMapping("/transfer")
    @ResponseStatus(HttpStatus.OK)
    public List<Transfer> getAllTransfers() {
        return transferService.getAllTransfers();
    }

    @Override
    @GetMapping("/transfer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transfer getTransferById(@PathVariable Long id) {
        return transferService.getTransferById(id);
    }

    @Override
    @GetMapping("/transfer/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transfer> getAllTransferFromAccount(@PathVariable Long id) {
        return transferService.getAllTransferFromAccount(id);
    }

    @Override
    @GetMapping("/transfer/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Transfer> findByAmountHigherThan(@RequestParam BigDecimal amount) {
        return transferService.findByAmountHigherThan(amount);
    }

    @Override
    @PatchMapping("/send")
    @ResponseStatus(HttpStatus.OK)
    public TransferResultAccountToAccountDTO transferFromAccountToAccount(@AuthenticationPrincipal UserDetails userDetails, @RequestBody TransferHolderToAccountDTO data) {
        return transferService.transferFromAccountToAccount(userDetails, data);
    }

    @Override
    @PatchMapping("/tp/send-in")
    @ResponseStatus(HttpStatus.OK)
    public TransferThirdPartyResultDTO transferFromThirdPartyToAccount(@RequestBody TransferThirdPartyDTO data) {
        return transferService.transferFromThirdPartyToAccount(data);
    }

    @Override
    @PatchMapping("/tp/send-out")
    @ResponseStatus(HttpStatus.OK)
    public TransferThirdPartyResultDTO transferFromAccountToThirdParty(@RequestBody TransferThirdPartyDTO data){
        return transferService.transferFromAccountToThirdParty(data);
    }

    @PatchMapping("/emit")
    @ResponseStatus(HttpStatus.OK)
    @Override
    public void transferThirdPartyOrAdminFromAccountToAccount(@RequestBody TransferThirdPartyWithinSystemDTO data) {
        transferService.transferThirdPartyOrAdminFromAccountToAccount(data);
    }
}
