package com.bqt.newspaper.repository;

import com.bqt.newspaper.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<User,String>, JpaSpecificationExecutor<User> {
    boolean existsByUsername(String username);
}
