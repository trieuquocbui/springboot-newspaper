package com.bqt.newspaer.repository;

import com.bqt.newspaer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);
}
