package com.example.familia.User;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.familia.User.User;
public interface UserRepository extends JpaRepository<User, Long> {}