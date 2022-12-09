package com.finalproject.ironhack;

import com.finalproject.ironhack.models.Accounts.Checking;
import com.finalproject.ironhack.models.Accounts.CreditCard;
import com.finalproject.ironhack.models.Accounts.Savings;
import com.finalproject.ironhack.models.Accounts.StudentChecking;
import com.finalproject.ironhack.models.Accounts.enums.Status;
import com.finalproject.ironhack.models.Agents.AccountHolder;
import com.finalproject.ironhack.models.Agents.Admin;
import com.finalproject.ironhack.models.Agents.ThirdParty;
import com.finalproject.ironhack.models.Agents.embedded.Address;
import com.finalproject.ironhack.models.Role;
import com.finalproject.ironhack.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@SpringBootApplication
public class IronhackApplication implements CommandLineRunner {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	AgentRepository agentRepository;

	@Autowired
	AdminRepository adminRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(IronhackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		accountHolderRepository.deleteAll();
		accountRepository.deleteAll();
		roleRepository.deleteAll();
		adminRepository.deleteAll();
		agentRepository.deleteAll();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		AccountHolder accountHolder = accountHolderRepository.save(new AccountHolder("Pepe", passwordEncoder.encode("Pepito"), format.parse("1984-03-02"), new Address("Sitges"), null));
		AccountHolder accountHolder2 = accountHolderRepository.save(new AccountHolder("Roboro", passwordEncoder.encode("Bogdoso"), format.parse("1966-03-02"), new Address("Vilanova"), null));
		AccountHolder accountHolder3 = accountHolderRepository.save(new AccountHolder("Mirella", passwordEncoder.encode("Traquea"), format.parse("1931-03-02"), new Address("Paracuellos"), null));
		AccountHolder accountHolder4 = accountHolderRepository.save(new AccountHolder("Bogdan", passwordEncoder.encode("Megador"), format.parse("2000-03-02"), new Address("Jarama"), null));

		Admin admin = adminRepository.save(new Admin("Mariano","Javatizador"));

		StudentChecking studentChecking = accountRepository.save(new StudentChecking(new BigDecimal("2000"),accountHolder, passwordEncoder.encode("secreta")));
		Checking checking = accountRepository.save(new Checking(new BigDecimal("2000"),accountHolder2, passwordEncoder.encode("secreto")));
		CreditCard creditCard = accountRepository.save(new CreditCard(new BigDecimal("800"), accountHolder3, new BigDecimal("50000"), new BigDecimal("0.18")));
		Savings savings = accountRepository.save(new Savings(new BigDecimal("1200"), accountHolder4, passwordEncoder.encode("secret"), new BigDecimal("777"), new BigDecimal("0.12")));

		accountHolder.setAccount(studentChecking);
		accountHolder2.setAccount(checking);
		accountHolder3.setAccount(creditCard);
		accountHolder4.setAccount(savings);

		accountHolderRepository.save(accountHolder);
		accountHolderRepository.save(accountHolder2);
		accountHolderRepository.save(accountHolder3);
		accountHolderRepository.save(accountHolder4);

		ThirdParty tp1 = agentRepository.save(new ThirdParty("TPV1",passwordEncoder.encode("123456")));
		ThirdParty tp2 = agentRepository.save(new ThirdParty("ING",passwordEncoder.encode("abcde")));

		roleRepository.save(new Role("ADMIN", admin));
		roleRepository.save(new Role("HOLDER", accountHolder));
		roleRepository.save(new Role("HOLDER", accountHolder2));
		roleRepository.save(new Role("HOLDER", accountHolder3));
		roleRepository.save(new Role("HOLDER", accountHolder4));
		roleRepository.save(new Role("THIRDPARTY", tp1));
		roleRepository.save(new Role("THIRDPARTY", tp2));

	}
}