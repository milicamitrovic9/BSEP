package com.ftn.bsep.service;

import java.util.Collection;

import com.ftn.bsep.model.Admin;

public interface AdminService {
	
    Collection<Admin> findAll();
    Admin findById(Long id);

    Admin findByEmail(String email);

    Admin create(Admin admin) throws Exception;

    Admin update(Admin admin) throws Exception;

    void delete(Long id);

    void deleteByEmail(String email);
    
}
