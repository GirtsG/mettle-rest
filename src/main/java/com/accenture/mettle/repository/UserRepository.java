package com.accenture.mettle.repository;

import com.accenture.mettle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String username);
}
