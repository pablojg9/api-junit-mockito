package com.gntech.api.resources;

import com.gntech.api.domain.UserDomain;
import com.gntech.api.dto.UserDTO;
import com.gntech.api.mappers.UserMapper;
import com.gntech.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserResource {

    private static final String ID = "/{id}";

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper mapper;

    @GetMapping(ID)
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.entityToDto(service.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserDTO> findAll = service.findAll().stream().map(mapper::entityToDto).collect(Collectors.toList());
        return ResponseEntity.ok().body(findAll);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id")
                .buildAndExpand(service.save(userDTO).getId())
                .toUri()).build();
    }

    @PutMapping(ID)
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        userDTO.setId(id);
        UserDomain userDomain = service.update(userDTO);
        return ResponseEntity.ok().body(mapper.entityToDto(userDomain));
    }

    @DeleteMapping(ID)
    public ResponseEntity<UserDTO> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
