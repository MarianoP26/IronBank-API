package com.finalproject.ironhack.controllers;

import com.finalproject.ironhack.controllers.interfaces.AgentControllerInterface;
import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Agents.Admin;
import com.finalproject.ironhack.models.Agents.Agent;
import com.finalproject.ironhack.models.Agents.ThirdParty;
import com.finalproject.ironhack.services.AccountService;
import com.finalproject.ironhack.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AgentController implements AgentControllerInterface {

    @Autowired
    AgentService agentService;

    @Override
    @GetMapping("/agents")
    @ResponseStatus(HttpStatus.OK)
    public List<Agent> getAllAgents() {
        return agentService.getAllAgents();
    }

    @Override
    @GetMapping("/agents/all")
    @ResponseStatus(HttpStatus.OK)
    public Agent getAgentByName(@RequestParam String name) {
        return agentService.getAgentByName(name);
    }

    @Override
    @GetMapping("/agents/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Agent getAgentById(@PathVariable Long id) {
        return agentService.getAgentById(id);
    }

    @Override
    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public Agent getUserData(@AuthenticationPrincipal UserDetails userDetails){
        return agentService.getUserData(userDetails);
    }



    @Override
    @PostMapping("/agents/add-third-party")
    @ResponseStatus(HttpStatus.OK)
    public ThirdParty addThirdPartyAccount(@RequestBody ThirdParty agent) {
        return agentService.addThirdPartyAccount(agent);
    }

    @Override
    @PostMapping("/agents/add-account-holder")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder addAccountHolderAccount(@RequestBody AccountHolder agent) {
        return agentService.addAccountHolderAccount(agent);
    }

    @Override
    @PostMapping("/agents/add-admin")
    @ResponseStatus(HttpStatus.OK)
    public Admin addAdminAccount(@RequestBody Admin agent) {
        return agentService.addAdminAccount(agent);
    }
}
