package com.beanstalk.beanstalk;
import com.beanstalk.beanstalk.Users;
import com.beanstalk.beanstalk.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    
    @Autowired
    private UsersRepository userRepository;

    public List<Users> list(){
        return userRepository.findAll();
    }

    public Users save(Users user){
        return userRepository.save(user);
    }

    public Users get(Long id){
        Optional<Users> userOptional =userRepository.findById(id);//optionals are used to prevent null poLongers, they can either container or a null or non null value
        if(userOptional.isEmpty()){
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "User not found");
        }
        return(userOptional.get());
    
    }

    public Users update(Long id, Users user){ //for now only updates the username
        Optional<Users> userOptional =userRepository.findById(id);
        if(userOptional.isEmpty()){
            throw new ResponseStatusException(
                org.springframework.http.HttpStatus.NOT_FOUND, "User not found");
        }
        Users currentUser=userOptional.get();
        currentUser.setUserName(user.getUserName());
        return(userRepository.save(currentUser));

    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }
}
