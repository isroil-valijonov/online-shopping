package com.example.onlineshopping.loader;

import com.example.onlineshopping.user.entity.User;
import com.example.onlineshopping.user.repository.UserRepository;
import com.example.onlineshopping.user.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String init;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        try {
            if (init.equalsIgnoreCase("create")){
                Role adminRole = Role.ADMIN;
                Role userRole = Role.USER;

                User admin = new User();
                admin.setPhoneNumber("998944906677");
                admin.setPassword(passwordEncoder.encode("123"));
                admin.setRole(adminRole);
                userRepository.save(admin);

                User user = new User();
                user.setFirstName("Ali");
                user.setLastName("Ali");
                user.setBirthDate(LocalDate.ofEpochDay(2024-10-10));
                user.setPhoneNumber("998200032201");
                user.setPassword("123");
                user.setRole(userRole);
                userRepository.save(user);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
