package com.spring.security.services;

import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;

import java.util.HashMap;

public interface IAuthService {

    public HashMap<String, String> login(LoginDTO login) throws Exception;

    public ResponseDTO registrar(UserEntity user) throws Exception;

}
