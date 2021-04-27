package com.ftn.bsep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.bsep.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
