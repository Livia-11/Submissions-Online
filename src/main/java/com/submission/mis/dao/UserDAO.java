package com.submission.mis.dao;

import com.submission.mis.model.User;
import java.util.Optional;

public interface UserDAO {
    void save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    boolean emailExists(String email);
} 