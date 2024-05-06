package com.example.onlineshopping.otp.repository;

import com.example.onlineshopping.otp.entity.OTP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends CrudRepository<OTP, String> {
}
