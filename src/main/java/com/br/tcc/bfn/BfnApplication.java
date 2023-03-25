package com.br.tcc.bfn;

import com.br.tcc.bfn.model.Role;
import com.br.tcc.bfn.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class BfnApplication{

	public static void main(String[] args) {
		SpringApplication.run(BfnApplication.class, args);
	}


}
