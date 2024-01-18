package com.spring.security.controllers;

import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.services.IAuthService;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/auth")
public class AuthControllers {

    @Autowired
    IAuthService authService;

    @PostMapping("/registrar")
    private ResponseEntity<ResponseDTO> registrar(@RequestBody UserEntity user) throws Exception {
        return new ResponseEntity<>(authService.registrar(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    private ResponseEntity<HashMap<String, String>> login(@RequestBody LoginDTO loginRequest) throws Exception {
        HashMap<String, String> login = authService.login(loginRequest);

        if (login.containsKey("jwt")) {
            return new ResponseEntity<>(login, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
        }

    }

    //Despues de crear el login y registrar tenemos que verificar en el service llamado como 'UserServiceImpl'

}
