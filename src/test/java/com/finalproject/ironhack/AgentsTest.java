package com.finalproject.ironhack;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Agents.Admin;
import com.finalproject.ironhack.models.Agents.ThirdParty;
import com.finalproject.ironhack.models.Agents.embedded.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AgentsTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }




    @Test
    void shouldReturnAllAgents_getAllAgents() throws Exception {
        MvcResult result = mockMvc.perform(get("/agents"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Pepe"));
        assertTrue(result.getResponse().getContentAsString().contains("Roboro"));
        assertTrue(result.getResponse().getContentAsString().contains("Mirella"));
        assertTrue(result.getResponse().getContentAsString().contains("Bogdan"));
    }

    @Test
    void shouldReturnAllAgentsWithSpecifiedName_getAllAgentsByName() throws Exception {
        MvcResult result = mockMvc.perform(get("/agents/all?name=Pepe"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Pepe"));
    }

    @Test
    void shouldReturnAgentById_getAgentById() throws Exception {
        MvcResult result = mockMvc.perform(get("/agents/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Pepe"));
    }

    @Test
    void shouldAddNewThirdPartyAgent_addNewThirdPartyAgent() throws Exception {
        ThirdParty thirdParty = new ThirdParty("TestParty", "hashedKey");
        String payload = objectMapper.writeValueAsString(thirdParty);
        MvcResult result = mockMvc.perform(post("/employees/add-third-party")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("TestParty"));
    }

    @Test
    void shouldAddNewAccountHolderAgent_addNewAccountHolderAgent() throws Exception {
        AccountHolder accountHolder = new AccountHolder("TestHolder", "password", new Date(), new Address("Address"), null);
        String payload = objectMapper.writeValueAsString(accountHolder);
        MvcResult result = mockMvc.perform(post("/employees/add-third-party")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("TestHolder"));
    }

    @Test
    void shouldAddNewAdminAgent_addNewAdminAgent() throws Exception {
        Admin admin = new Admin("TestAdmin", "password");
        String payload = objectMapper.writeValueAsString(admin);
        MvcResult result = mockMvc.perform(post("/employees/add-third-party")
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("TestAdmin"));
    }





}
