package com.gntech.api.services.impl;

import com.gntech.api.domain.UserDomain;
import com.gntech.api.dto.UserDTO;
import com.gntech.api.mappers.UserMapper;
import com.gntech.api.repositories.UserRepository;
import com.gntech.api.services.UserService;
import com.gntech.api.services.exceptions.DataIntegrityViolationException;
import com.gntech.api.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl( UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDomain findById(Integer id) {
        Optional<UserDomain> userDomain = userRepository.findById(id);
        return userDomain.orElseThrow(() -> new ObjectNotFoundException("user not found"));
    }

    @Override
    public List<UserDomain> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDomain save(UserDTO userDTO) {
        findByEmail(userDTO);
        return userRepository.save(userMapper.dtoToEntity(userDTO));
    }

    @Override
    public UserDomain update(UserDTO dto) {
        findByEmail(dto);
        return userRepository.save(userMapper.dtoToEntity(dto));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void findByEmail(UserDTO userDTO) {
        userRepository.findByEmail(userDTO.getEmail()).filter(x -> !x.getId().equals(userDTO.getId())).ifPresent(x -> {
            throw new DataIntegrityViolationException("Email ja existe na base de dados");
        });
    }
}
