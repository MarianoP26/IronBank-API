package com.finalproject.ironhack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.repositories.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    void shouldReturnAllAccounts_getAllAccounts() throws Exception {
        MvcResult result = mockMvc.perform(get("/accounts"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("2000"));
        assertTrue(result.getResponse().getContentAsString().contains("777"));
        assertTrue(result.getResponse().getContentAsString().contains("50000"));
        assertTrue(result.getResponse().getContentAsString().contains("2012"));
    }

    @Test
    void shouldReturnAccountById_getAccountById() throws Exception {
        MvcResult result = mockMvc.perform(get("/accounts/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("2021"));
    }

    @Test
    void shouldReturnAccountByAccountHolderId_getAccountByAccountHolderId() throws Exception {
        MvcResult result = mockMvc.perform(get("/accounts/all?id=1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("2021"));
    }

    @Test
    void shouldUpdateAccountBalance_updateAccountBalance() throws Exception {
        MvcResult result = mockMvc.perform(patch("/accounts/1?newBalance=33333"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("33333"));
    }
}
