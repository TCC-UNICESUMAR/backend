package com.br.tcc.bfn;

import com.br.tcc.bfn.models.Role;
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

	@Override
	public void run(ApplicationArguments args) throws Exception {
	}
}
