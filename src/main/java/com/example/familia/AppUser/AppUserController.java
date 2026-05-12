package com.example.familia.AppUser;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class AppUserController {
    private final AppUserService userService;

    @GetMapping
    public List<AppUser> getAllUsers(){
        return userService.getAllUsers();
    }
    
    
    @GetMapping("/{id}")
    public AppUser getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user){
        return userService.createUser(user);
    }
    
    @PutMapping("/{id}")
    public AppUser updateUser(@PathVariable Long id, @RequestBody AppUser user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}