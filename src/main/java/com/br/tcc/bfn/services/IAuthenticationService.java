package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dtos.AuthenticationRequest;
import com.br.tcc.bfn.dtos.Response;

public interface IAuthenticationService {

    Response<String> authenticate(AuthenticationRequest request);
}
