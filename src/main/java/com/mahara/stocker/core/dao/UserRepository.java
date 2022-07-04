package com.mahara.stocker.core.dao;

import com.mahara.stocker.core.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>, UserJdbc {}
