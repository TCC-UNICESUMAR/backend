package com.br.tcc.bfn.services;

import com.br.tcc.bfn.dto.AuthenticationRequest;
import com.br.tcc.bfn.dto.AuthenticationResponse;
import com.br.tcc.bfn.dto.Response;

public interface IAuthenticationService {

    public Response<String> authenticate(AuthenticationRequest request);
}
