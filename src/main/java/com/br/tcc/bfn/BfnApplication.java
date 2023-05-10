package com.br.tcc.bfn;

import com.br.tcc.bfn.repositories.UserRepository;
import com.br.tcc.bfn.s3.S3Buckets;
import com.br.tcc.bfn.s3.S3Service;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BfnApplication{

	public static void main(String[] args) {
		SpringApplication.run(BfnApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000");
			}
		};
	}

	@Bean
	CommandLineRunner runner(
			UserRepository customerRepository,
			PasswordEncoder passwordEncoder,
			S3Service s3Service,
			S3Buckets s3Buckets) {
		return args -> {
			testBucketUploadAndDownload(s3Service, s3Buckets);
		};
	}

	private static void testBucketUploadAndDownload(S3Service s3Service,
													S3Buckets s3Buckets) {
		s3Service.putObject(
				s3Buckets.getCustomer(),
				"foo",
				"Hello World".getBytes()
		);

		byte[] obj = s3Service.getObject(
				s3Buckets.getCustomer(),
				"foo"
		);

		System.out.println("Hooray: " + new String(obj));
	}


}
