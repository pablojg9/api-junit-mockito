package com.gntech.api.resources;

import com.gntech.api.domain.UserDomain;
import com.gntech.api.dto.UserDTO;
import com.gntech.api.mappers.UserMapper;
import com.gntech.api.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class UserResourceTest {

    private static final Integer ID = 1;
    private static final String NAME = "pablo";
    private static final String EMAIL = "pablo@gmail.com";
    private static final String PASSWORD = "1234";
    private static final String USER_NOT_FOUND = "User not found";
    private static final int INDEX = 0;
    private static final String EXPECTED = "Email ja existe na base de dados";

    @InjectMocks
    private UserResource userResource;

    @Mock
    private UserService service;

    @Mock
    private UserMapper mapper;

    private UserDomain userDomain;
    private UserDTO userDTO;


    @BeforeEach
    void setUp() {
        openMocks(this);
        startUser();
    }

    @Test
    @DisplayName("when find by then return success")
    void whenFindByIdThenReturnSuccess() {

        when(service.findById(anyInt())).thenReturn(userDomain);
        when(mapper.entityToDto(any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> response = userResource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME, response.getBody().getName());
        assertEquals(EMAIL, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }
    @Test
    @DisplayName("when find all then return a list of userDTO")
    void whenFindAllThenReturnAListOfUserDTO() {
        when(service.findAll()).thenReturn(Collections.singletonList(userDomain));
        when(mapper.entityToDto(any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> response = userResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());


        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(NAME, response.getBody().get(INDEX).getName());
        assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());
    }

    @Test
    @DisplayName("when create then return status created")
    void whenCreateThenReturnStatusCreated() {
        when(service.save(any())).thenReturn(userDomain);

        ResponseEntity<UserDTO> response = userResource.create(userDTO);
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().get("location"));
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    public void startUser() {
        userDomain = new UserDomain(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}