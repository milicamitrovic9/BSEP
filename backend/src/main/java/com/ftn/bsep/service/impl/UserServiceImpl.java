package com.ftn.bsep.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ftn.bsep.model.User;
import com.ftn.bsep.model.UserDTO;
import com.ftn.bsep.repository.UserRepository;
import com.ftn.bsep.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Collection<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).orElseGet(null);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public User create(UserDTO userDTO) throws Exception {
		
		User user = new User();
		String password = passwordEncoder.encode(userDTO.getPassword());
		
		user.setPassword(password);
		user.setName(userDTO.getName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		user = userRepository.save(user);

		return user;
	}

	@Override
	public User update(User user) throws Exception {
		return null;
	}

	@Override
	public void delete(Long id) {

	}
}
