package com.gntech.api.services;

import com.gntech.api.domain.UserDomain;
import com.gntech.api.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDomain findById(Integer id);

    List<UserDomain> findAll();

    UserDomain save(UserDTO UserDTO);

    UserDomain update(UserDTO dto);
    void delete(Integer id);
}
