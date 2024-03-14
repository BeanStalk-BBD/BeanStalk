package com.beanstalk.backend.service;

import com.beanstalk.backend.domain.User;
import com.beanstalk.backend.model.UserDTO;
import com.beanstalk.backend.repos.ChatRepository;
import com.beanstalk.backend.repos.MessageRepository;
import com.beanstalk.backend.repos.UserRepository;
import com.beanstalk.backend.util.NotFoundException;
import com.beanstalk.backend.util.ReferencedWarning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Mocking data
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        userList.add(new User());
    
        when(userRepository.findAll()).thenReturn(userList);
    
        // Call the method under test
        List<UserDTO> userDTOList = userService.findAll();
    
        // Verify
        assertEquals(2, userList.size());
    }

    @Test
    void testGet() {
        // Mocking data
        User user = new User();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        // Call the method under test
        UserDTO userDTO = userService.get(1);

        // Verify
        assertNotNull(userDTO);
    }

    // Add more test cases for other methods as needed
}
