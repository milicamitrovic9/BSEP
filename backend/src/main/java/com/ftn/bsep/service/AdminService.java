package com.ftn.bsep.service;

import java.util.Collection;
import com.ftn.bsep.model.Admin;
import com.ftn.bsep.model.UserDTO;

public interface AdminService {
	
    Collection<Admin> findAll();
    Admin findById(Long id);

    Admin findByEmail(String email);

    Admin create(UserDTO admin) throws Exception;

    Admin update(Admin admin) throws Exception;

    void delete(Long id);

    void deleteByEmail(String email);
    
}
