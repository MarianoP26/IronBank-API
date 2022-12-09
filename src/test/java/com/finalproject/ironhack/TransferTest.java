package com.finalproject.ironhack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.ironhack.DTOs.TransferHolderToAccountDTO;
import com.finalproject.ironhack.DTOs.TransferResultAccountToAccountDTO;
import com.finalproject.ironhack.DTOs.TransferThirdPartyDTO;
import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Accounts.enums.Status;
import com.finalproject.ironhack.models.Accounts.interfaces.SecretKeyStatusAccount;
import com.finalproject.ironhack.models.Transfer;
import com.finalproject.ironhack.repositories.AccountRepository;
import com.finalproject.ironhack.repositories.TransferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class TransferTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    AccountRepository accountRepository;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void shouldReturnAllTransfers_getAllTransfers() throws Exception {
        Transfer transfer = transferRepository.save(new Transfer(new BigDecimal("50"), accountRepository.findById(1L).get(), accountRepository.findById(2L).get()));
        MvcResult result = mockMvc.perform(get("/transfer"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("50"));
    }

    @Test
    void shouldReturnTransferById_getTransferById() throws Exception {
        Transfer transfer = transferRepository.save(new Transfer(new BigDecimal("50"), accountRepository.findById(1L).get(), accountRepository.findById(2L).get()));
        MvcResult result = mockMvc.perform(get("/transfer/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("50"));
    }

    @Test
    void shouldReturnAllTransferByAccountId_getAllTransferByAccountId() throws Exception {
        Transfer transfer = transferRepository.save(new Transfer(new BigDecimal("50"), accountRepository.findById(1L).get(), accountRepository.findById(2L).get()));
        MvcResult result = mockMvc.perform(get("/transfer/user/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("50"));
    }

    @Test
    void shouldReturnAllTransfersWithAmountHigherThan_getAllTransfersHigherThan() throws Exception {
        Transfer transfer = transferRepository.save(new Transfer(new BigDecimal("50"), accountRepository.findById(1L).get(), accountRepository.findById(2L).get()));
        MvcResult result = mockMvc.perform(get("/transfer/all?amount=25"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("50"));
    }

    //----------------------------Transactions PATCH -------------------------------------

    @Test
    void shouldThirdPartySendToAccount_transferFromThirdPartyToAccount() throws Exception {
        TransferThirdPartyDTO data = new TransferThirdPartyDTO(1L, "secreta", new BigDecimal("50"));
        String payload = objectMapper.writeValueAsString(data);
        MvcResult result = mockMvc.perform(patch("/transfer/tp/send-in")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(new BigDecimal("2050.00"), accountRepository.findById(1L).get().getBalance());
    }

    @Test
    void shouldAccountSendToThirdParty_transferFromAccountToThirdParty() throws Exception {
        TransferThirdPartyDTO data = new TransferThirdPartyDTO(1L, "secreta", new BigDecimal("50"));
        String payload = objectMapper.writeValueAsString(data);
        MvcResult result = mockMvc.perform(patch("/transfer/tp/send-out")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(new BigDecimal("1950.00"), accountRepository.findById(1L).get().getBalance());
    }

    @Test
    void shouldApplyPenaltyFee_applyPenaltyFee() throws Exception {
        TransferThirdPartyDTO data = new TransferThirdPartyDTO(2L, "secreto", new BigDecimal("1751"));
        String payload = objectMapper.writeValueAsString(data);
        MvcResult result = mockMvc.perform(patch("/transfer/tp/send-out")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(new BigDecimal("209.00"), accountRepository.findById(2L).get().getBalance());
    }

    @Test
    void shouldFreezeAccountUponMakingTwoTransactionInOneSecond_freezeAccount() throws Exception {
        TransferThirdPartyDTO data = new TransferThirdPartyDTO(2L, "secreto", new BigDecimal("50"));
        String payload = objectMapper.writeValueAsString(data);
        MvcResult result = mockMvc.perform(patch("/transfer/tp/send-out")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        SecretKeyStatusAccount account = (SecretKeyStatusAccount) accountRepository.findById(2L).get();
        assertEquals(Status.FROZEN, account.getStatus());
    }


    @Test
    void shouldAccountSendToAccount_transferFromAccountToAccount() throws Exception {
        // Security not implemented on test. Unable to get Origin Account
        // Jose Caro told me to leave it like this.
        Account origin = accountRepository.findById(2L).get();   //Origin Balance for AccountId 2 is 2000
        TransferHolderToAccountDTO data = new TransferHolderToAccountDTO(1L, "Pepe", new BigDecimal("50"));  //Destiny Balance for AccountId 1 is 2000
        String payload = objectMapper.writeValueAsString(data);
        MvcResult result = mockMvc.perform(patch("/send")  // <---- problem
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        assertEquals(new BigDecimal("2050.00"), accountRepository.findById(1L).get().getBalance()); //Destiny should now have 2050 balance
        assertEquals(new BigDecimal("1950.00"), accountRepository.findById(2L).get().getBalance()); //Destiny should now have 1950 balance
    }
}
