package com.ftn.bsep.service;

import java.util.Collection;

import com.ftn.bsep.model.User;

public interface UserService {
	
	Collection<User> findAll();

	User findById(Long id);

	User findByEmail(String email);

	User create(User user) throws Exception;

	User update(User user) throws Exception;

	void delete(Long id);
}
