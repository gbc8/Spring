package com.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.crud.model.User;

public interface UserRepository extends JpaRepository<User,String> {

	UserDetails findByUsername(String username);
}
