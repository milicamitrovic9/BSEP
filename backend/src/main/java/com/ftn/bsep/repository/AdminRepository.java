package com.ftn.bsep.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.bsep.model.Admin;

public interface AdminRepository extends JpaRepository <Admin,Long>{

    Admin findByEmail(String email);

}