package com.example.onlineshopping.user.repository;

import com.example.onlineshopping.common.repository.GenericRepository;
import com.example.onlineshopping.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends GenericRepository<User, UUID> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true, value = "select u.* from users u where u.id=?1")
    User findByUserId(UUID id);



}
