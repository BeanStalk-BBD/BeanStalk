package com.beanstalk.backend.repos;

import com.beanstalk.backend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUserNameIgnoreCase(String userName);
    
    User findByUserName(String userName);
}
