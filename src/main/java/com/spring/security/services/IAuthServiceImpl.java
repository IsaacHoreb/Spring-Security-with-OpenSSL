package com.spring.security.services;

import com.spring.security.services.models.dtos.LoginDTO;

import java.util.HashMap;

public interface IAuthServiceImpl {

    public HashMap<String, String> login(LoginDTO login) throws Exception;

}
