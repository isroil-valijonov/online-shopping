package com.example.onlineshopping.user.repository;

import com.example.onlineshopping.common.repository.GenericRepository;
import com.example.onlineshopping.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericRepository<User, UUID> {
    Optional<User> findByPhoneNumber(String phoneNumber);
}
