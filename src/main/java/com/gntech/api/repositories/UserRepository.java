package com.gntech.api.repositories;

import com.gntech.api.domain.UserDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDomain, Integer> {

    Optional<UserDomain> findByEmail(String email);
}
