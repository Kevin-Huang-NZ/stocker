package com.mahara.stocker.core.dao;

import com.mahara.stocker.core.model.User;
import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.response.PaginationOut;

import java.util.Optional;

public interface UserJdbc {
  PaginationOut<User> search(String keyword, PaginationIn pi);

  Optional<User> findByUniqueKey(String phone, Long id);
}
