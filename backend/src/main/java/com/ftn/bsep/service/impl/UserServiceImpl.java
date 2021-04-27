package com.ftn.bsep.service.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import com.ftn.bsep.model.UserDTO;
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
    public User create(UserDTO user) throws Exception {
        String password=user.getPassword();
        byte[] dataHash = hash(password);
        User ret = new User();
        ret.setPassword(dataHash);
        ret.setName(user.getName());
        ret.setLastName( user.getLastName());
        ret.setEmail(user.getEmail());
        //ret.copyValues(korisnik);
        ret = userRepository.save(ret);

        return ret;
    }

    public byte[] hash(String data) {
        //Kao hes funkcija koristi SHA-256
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] dataHash = sha256.digest(data.getBytes());
            return dataHash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User update(User user) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
