package com.ftn.bsep.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.bsep.model.User;
import com.ftn.bsep.repository.UserRepository;
import com.ftn.bsep.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
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
    public User create(User user) throws Exception {
    	User ret = new User();
        ret.copyValues(user);
        ret = userRepository.save(ret);

        return ret;
    }

    @Override
    public User update(User korisnik) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
