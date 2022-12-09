package com.finalproject.ironhack.services.interfaces;

import com.finalproject.ironhack.models.Accounts.Account;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Agents.Admin;
import com.finalproject.ironhack.models.Agents.Agent;
import com.finalproject.ironhack.models.Agents.ThirdParty;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AgentServiceInterface {

    List<Agent> getAllAgents();
    Agent getAgentByName(String name);
    Agent getAgentById(Long id);

    Agent getUserData( UserDetails userDetails);

    ThirdParty addThirdPartyAccount(ThirdParty agent);
    AccountHolder addAccountHolderAccount(AccountHolder agent);

    Admin addAdminAccount(Admin agent);

}
