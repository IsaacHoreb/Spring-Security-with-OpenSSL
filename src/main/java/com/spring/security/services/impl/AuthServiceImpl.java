package com.spring.security.services.impl;

import com.spring.security.persistence.entity.UserEntity;
import com.spring.security.persistence.repository.UserRepository;
import com.spring.security.services.IAuthService;
import com.spring.security.services.IJWTUtilityService;
import com.spring.security.services.models.dtos.LoginDTO;
import com.spring.security.services.models.dtos.ResponseDTO;
import com.spring.security.services.models.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IJWTUtilityService jwtUtilityService;

    @Autowired
    private UserValidation userValidation;

    // -> 1ro
    @Override
    public HashMap<String, String> login(LoginDTO login) throws Exception {
        try {
            HashMap<String, String> jwt = new HashMap<>();

            Optional<UserEntity> user = userRepository.findByEmail(login.getEmail());

            //Por si esta vacio
            if (user.isEmpty()) {
                jwt.put("error", "User not registered");
                return jwt;
            }

            //Verificamos el password pero debemos crear el metodo abajo
            if (verifyPassword(login.getPassword(), user.get().getPassword())) {
                jwt.put("jwt", jwtUtilityService.generateJWT(user.get().getId()));
            } else {
                jwt.put("error", "Authentication failed");
            }

            return jwt;

        } catch (IllegalArgumentException e) {
            System.err.println("Error generating JWT: " + e.getMessage());
            throw new Exception("Error generating JWT", e);
        } catch (Exception e) {
            System.err.println("Unknown error: " + e.toString());
            throw new Exception("Unknown error", e);
        }

    }

    // -> 3ro
    @Override
    public ResponseDTO registrar(UserEntity user) throws Exception {
        try {
            ResponseDTO response = userValidation.validation(user); //Para validar

            List<UserEntity> getAllUsers = userRepository.findAll();

            if (response.getNumOfErrors() > 0) {
                return response;
            }

            for (UserEntity repeatField : getAllUsers) {
                if (repeatField != null) {

                    response.setNumOfErrors(1);

                    response.setMessage("User already exists!");
                    return response;
                }
            }

            //Encriptamos la contrasenia
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(user.getPassword()));

            userRepository.save(user); //Lo guardamos y es en la BD
            response.setMessage("User created succesfully!");

            return response;

        } catch (Exception e) {
            throw new Exception(e.toString());
        }

    }

    // -> 2do Para que lo podamos usar en el login arriba
    private boolean verifyPassword(String enteredPassword, String storedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.matches(enteredPassword, storedPassword);

    }

}
