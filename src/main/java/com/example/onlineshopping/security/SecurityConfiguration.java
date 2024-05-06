package com.example.onlineshopping.security;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//public class SecurityConfiguration {
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity
//                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        registry -> registry
//                                .requestMatchers("/user/auth/**")
//                                .permitAll()
//                                .anyRequest()
//                                .authenticated()
//                )
//                .build();
//    }
//}
