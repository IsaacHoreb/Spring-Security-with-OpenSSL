package com.spring.security.services;

import com.spring.security.persistence.entity.UserEntity;

import java.util.List;

public interface IUserService {

    List<UserEntity> findAllUsers();


}
