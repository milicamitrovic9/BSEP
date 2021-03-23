package com.ftn.bsep.repository;

import com.ftn.bsep.model.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {

    User findByEmail(String email);
}
