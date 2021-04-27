package com.ftn.bsep.service;

import java.util.Collection;

import com.ftn.bsep.model.User;
import com.ftn.bsep.model.UserDTO;

public interface UserService {
	
	Collection<User> findAll();

	User findById(Long id);

	User findByEmail(String email);

	User create(UserDTO user) throws Exception;

	User update(User user) throws Exception;

	void delete(Long id);
}
