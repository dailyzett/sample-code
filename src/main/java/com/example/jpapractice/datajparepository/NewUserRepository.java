package com.example.jpapractice.datajparepository;

import com.example.jpapractice.entity.User;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface NewUserRepository extends Repository<User, String> {
    Optional<User> save(User user);

    User findByEmail(String email);

    void delete(User user);

    Optional<User> findByName(String name);
}
