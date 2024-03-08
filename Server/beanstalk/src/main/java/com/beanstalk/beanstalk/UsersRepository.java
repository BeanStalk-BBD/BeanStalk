package com.beanstalk.beanstalk;
import com.beanstalk.beanstalk.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UsersRepository extends JpaRepository<Users,Long>{
    
}
