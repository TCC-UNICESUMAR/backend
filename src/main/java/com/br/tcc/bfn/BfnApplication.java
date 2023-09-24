package com.br.tcc.bfn;

import com.br.tcc.bfn.models.DateCustom;
import com.br.tcc.bfn.models.Role;
import com.br.tcc.bfn.repositories.DateRepository;
import com.br.tcc.bfn.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Arrays;

@SpringBootApplication
@EnableCaching
public class BfnApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(BfnApplication.class, args);
	}
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private DateRepository dateRepository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		DateCustom date1 = new DateCustom();
		DateCustom date2 = new DateCustom();
		DateCustom date3 = new DateCustom();
		dateRepository.saveAll(Arrays.asList(date1,date2,date3));

		Role roleDefault = new Role("ROLE_USER",date1);
		Role ong = new Role("ROLE_ONG",date2);
		Role admin = new Role("ROLE_ADMIN",date3);

		roleRepository.saveAll(Arrays.asList(roleDefault,admin,ong));
	}
}
