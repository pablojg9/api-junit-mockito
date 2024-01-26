package com.gntech.api.config;

import com.gntech.api.domain.UserDomain;
import com.gntech.api.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;


/**
 * Classe de configuracao para @profile('local') para fazer a insert do {@link UserDomain} a fins de testes e chamar via postman.
 *
 * @Author pablojg9
 * @version 0.0.1
 * */
@Configuration
@Profile("local")
public class LocalConfig {

    private final UserRepository userRepository;

    @Autowired
    public LocalConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public List<UserDomain> startDB() {
        UserDomain user1 = new UserDomain(null, "pablo", "pablo@gmail.com", "1234");
        UserDomain user2 = new UserDomain(null, "Junior", "junior@gmail.com", "4321");

        return userRepository.saveAll(List.of(user1, user2));
    }
}
