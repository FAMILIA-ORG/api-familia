package com.example.familia.User;

import org.springframework.stereotype.Service;
import com.example.familia.User.User;
import com.example.familia.User.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(User user){
        return userRepository.save(user);
    }
    
    public User updateUser(Long id, User data){
        User user = getUserById(id);

        user.setRole(data.getRole());
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());
        user.setIsActive(data.getIsActive());
        user.setPersonne(data.getPersonne());

        return userRepository.save(user);
    }
    
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}   