package com.br.tcc.bfn.auth;

import com.br.tcc.bfn.model.User;
import com.br.tcc.bfn.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class AuthenticationSuccessHandlerCustomize implements AuthenticationSuccessHandler {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationSuccessHandlerCustomize(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        final Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        DefaultOidcUser user = (DefaultOidcUser) principal.getPrincipal();
        final Optional<User> byEmail = repository.findByEmail(user.getEmail());
        if(byEmail.isEmpty()){
            User userModel = new User();
            userModel.setEmail(user.getEmail());
            userModel.setFirstname(Arrays.toString(user.getName().split(" ")));
            userModel.setLastname(user.getFamilyName());
            userModel.setPassword(passwordEncoder.encode("1234"));
            repository.save(userModel);
        }

        response.sendRedirect("http://localhost:8080/api/auth/findAll");
    }
}
