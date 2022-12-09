package com.finalproject.ironhack.controllers.interfaces;

import com.finalproject.ironhack.DTOs.*;
import com.finalproject.ironhack.models.Transfer;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;

public interface TransferControllerInterface {

    List<Transfer> getAllTransfers();
    Transfer getTransferById(Long id);
    List<Transfer> getAllTransferFromAccount(Long id);
    List<Transfer> findByAmountHigherThan(BigDecimal amount);

    TransferResultAccountToAccountDTO transferFromAccountToAccount(UserDetails userDetails, TransferHolderToAccountDTO data);

    TransferThirdPartyResultDTO transferFromThirdPartyToAccount(TransferThirdPartyDTO data);

    TransferThirdPartyResultDTO transferFromAccountToThirdParty(TransferThirdPartyDTO data);
    void transferThirdPartyOrAdminFromAccountToAccount(TransferThirdPartyWithinSystemDTO data);

}
