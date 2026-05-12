package com.example.familia.AppUser;

import org.springframework.stereotype.Service;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService {
    private final AppUserRepository userRepository;
    public List<AppUser> getAllUsers(){
        return userRepository.findAll();
    }
    
    public AppUser getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public AppUser createUser(AppUser user){
        return userRepository.save(user);
    }
    
    public AppUser updateUser(Long id, AppUser data){
        AppUser user = getUserById(id);

        user.setRole(data.getRole());
        user.setUsername(data.getUsername());
        user.setPassword(data.getPassword());
        user.setEmail(data.getEmail());
        user.setActive(data.isActive());
        user.setPersonne(data.getPersonne());

        return userRepository.save(user);
    }
    
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}   