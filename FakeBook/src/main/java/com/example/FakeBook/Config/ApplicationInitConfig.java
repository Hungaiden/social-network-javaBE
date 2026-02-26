package com.example.FakeBook.Config;

import com.example.FakeBook.Entity.User;
import com.example.FakeBook.Enums.Role;
import com.example.FakeBook.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class ApplicationInitConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${username_admin}")
    private String usernameAdmin;

    @Value("${password_admin}")
    private String passwordAdmin;

    @Value("${email_admin}")
    private String emailAdmin;


    @Bean
    ApplicationRunner init(UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsByUsernameAndRole("admin", Role.ADMIN.name()) && !userRepository.existsByEmail(emailAdmin)) {
                User user = User.builder()
                        .username(usernameAdmin)
                        .password(passwordEncoder.encode(passwordAdmin))
                        .email(emailAdmin)
                        .role(Role.ADMIN.name())
                        .displayName("Admin")
                        .build();
                userRepository.save(user);
                log.warn("Admin da duoc tao");
            }
        };
    }

}
