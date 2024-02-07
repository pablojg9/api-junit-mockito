package com.gntech.api.services.impl;

import com.gntech.api.domain.UserDomain;
import com.gntech.api.dto.UserDTO;
import com.gntech.api.mappers.UserMapper;
import com.gntech.api.repositories.UserRepository;
import com.gntech.api.services.exceptions.DataIntegrityViolationException;
import com.gntech.api.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    private static final Integer ID = 1;
    private static final String NAME = "pablo";
    private static final String EMAIL = "pablo@gmail.com";
    private static final String PASSWORD = "1234";
    private static final String USER_NOT_FOUND = "User not found";
    private static final int INDEX = 0;
    private static final String EXPECTED = "Email ja existe na base de dados";

    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    private UserDomain userDomain;
    private UserDTO userDTO;
    private Optional<UserDomain> optionalUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUser();
    }

    @Test
    @DisplayName("when find by id then return and user instance")
    void whenFindByIdThenReturnAndUserInstance() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        UserDomain response = service.findById(ID);

        assertNotNull(response);
        assertEquals(UserDomain.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    @DisplayName("when find by id then return an object not exception")
    void whenFindByIdThenReturnAnObjectNotException() {
        when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(USER_NOT_FOUND));
        try {
            service.findById(ID);
        }catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(USER_NOT_FOUND, ex.getMessage());
        }
    }

    @Test
    @DisplayName("when find all then return an list of users")
    void whenFindAllThenReturnAnListOfUsers() {
        when(userRepository.findAll()).thenReturn(List.of(userDomain));
        List<UserDomain> response = service.findAll();
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(UserDomain.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME, response.get(INDEX).getName());
        assertEquals(EMAIL, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());
    }

    @Test
    @DisplayName("when create user then return success")
    void whenCreateUserThenReturnSuccess() {
        when(userRepository.save(any())).thenReturn(userDomain);
        UserDomain response = service.save(userDTO);
        assertNotNull(response);
        assertEquals(UserDomain.class, response.getClass());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    @DisplayName("when create user then return an data integrity violation exception")
    void whenCreateUserThenReturnAnDataIntegrityViolationException() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.save(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(EXPECTED, ex.getMessage());
        }
    }

    @Test
    @DisplayName("when update user then return success")
    void whenUpdateUserThenReturnSuccess() {
        when(userRepository.save(any())).thenReturn(userDomain);
        UserDomain response = service.update(userDTO);
        assertNotNull(response);
        assertEquals(UserDomain.class, response.getClass());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    @DisplayName("when update user then return an data integrity violation exception")
    void whenUpdateUserThenReturnAnDataIntegrityViolationException() {
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            optionalUser.get().setId(2);
            service.save(userDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals(EXPECTED, ex.getMessage());
        }
    }

    @Test
    @DisplayName("when delete user id return success")
    void whenDeleteUserIdReturnSuccess() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyInt());
        service.delete(ID);
        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    @DisplayName("when delete user id then return object not found exception")
    void whenDeleteUserIdThenReturnObjectNotFoundException() {
        when(userRepository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(USER_NOT_FOUND));
        try {
            doNothing().when(userRepository).deleteById(anyInt());
            service.delete(ID);
            verify(userRepository, times(1)).deleteById(anyInt());
        } catch (Exception ex) {
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals(USER_NOT_FOUND, ex.getMessage());
        }
    }

    public void startUser() {
        userDomain = new UserDomain(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new UserDomain(ID, NAME, EMAIL, PASSWORD));
    }
}