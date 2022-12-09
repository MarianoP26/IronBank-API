package com.finalproject.ironhack.services;

import com.finalproject.ironhack.consts.Consts;
import com.finalproject.ironhack.models.Agents.Agent;
import com.finalproject.ironhack.repositories.AgentRepository;
import com.finalproject.ironhack.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    AgentRepository agentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Agent> agent = agentRepository.findByName(username);

        if(!agent.isPresent()) {
            throw new UsernameNotFoundException(Consts.ACCOUNT_ACCOUNT_HOLDER_NOT_FOUND_ERROR);
        }

        CustomUserDetails customUserDetails = new CustomUserDetails(agent.get());

        return customUserDetails;
    }

}
