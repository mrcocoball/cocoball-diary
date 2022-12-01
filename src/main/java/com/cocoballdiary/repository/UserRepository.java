package com.cocoballdiary.repository;

import com.cocoballdiary.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
