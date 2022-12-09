package com.finalproject.ironhack.services;

import com.finalproject.ironhack.consts.Consts;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Agents.Admin;
import com.finalproject.ironhack.models.Agents.Agent;
import com.finalproject.ironhack.models.Agents.ThirdParty;
import com.finalproject.ironhack.models.Agents.embedded.Address;
import com.finalproject.ironhack.models.Role;
import com.finalproject.ironhack.repositories.AgentRepository;
import com.finalproject.ironhack.repositories.RoleRepository;
import com.finalproject.ironhack.services.interfaces.AgentServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static com.finalproject.ironhack.consts.Consts.*;

import java.util.Date;
import java.util.List;

@Service
public class AgentService implements AgentServiceInterface {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Agent getAgentByName(String name) {
        return agentRepository.findByName(name).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AGENT_NOT_FOUND_ERROR));
    }

    @Override
    public Agent getAgentById(Long id) {
        return agentRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AGENT_NOT_FOUND_ERROR));
    }

    @Override
    public Agent getUserData(UserDetails userDetails){
        if (userDetails == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, AGENT_MUST_BE_LOGGED_IN_ERROR);
        return agentRepository.findByName(userDetails.getUsername()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, AGENT_MUST_BE_LOGGED_IN_ERROR));
    }

    @Override
    public ThirdParty addThirdPartyAccount(ThirdParty agent) {
        ThirdParty thirdparty = new ThirdParty(agent.getName(), passwordEncoder.encode(agent.getPassword()));
        roleRepository.save(new Role(Consts.THIRDPARTY, thirdparty));
        return agentRepository.save(thirdparty);
    }

    @Override
    public AccountHolder addAccountHolderAccount(AccountHolder agent) {
        String name = agent.getName();
        String password = passwordEncoder.encode(agent.getPassword());
        Date dateOfBirth = agent.getDateOfBirth();
        Address address = agent.getPrimaryAddress();
        Address mailingAddress = agent.getMailingAddress();
        AccountHolder accountHolder = new AccountHolder(name, password, dateOfBirth, address, mailingAddress);
        roleRepository.save(new Role(Consts.HOLDER, accountHolder));
        return agentRepository.save(accountHolder);
    }

    @Override
    public Admin addAdminAccount(Admin agent) {
        Admin admin = new Admin(agent.getName(), passwordEncoder.encode(agent.getPassword()));
        agentRepository.save(admin);
        roleRepository.save(new Role(Consts.ADMIN, admin));
        return admin;
    }
}
