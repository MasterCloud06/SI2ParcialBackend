package com.example.historiaclinica.controller;

import com.example.historiaclinica.dto.RoleAssignmentDto;
import com.example.historiaclinica.dto.UserProfileDto;
import com.example.historiaclinica.dto.UserRegistrationDto;
import com.example.historiaclinica.dto.UserUpdateDto;
import com.example.historiaclinica.model.Role;
import com.example.historiaclinica.model.RoleName;
import com.example.historiaclinica.model.Users;
import com.example.historiaclinica.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint para registrar un nuevo usuario con roles
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userDto) {
        Users user = new Users();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        Set<RoleName> roleNames = userDto.getRoles();

        // Convertir RoleName a Role usando userService
        Set<Role> roles = roleNames.stream()
                .map(userService::getRoleByName)
                .collect(Collectors.toSet());

        Users newUser = userService.registerUser(user, roles);
        return ResponseEntity.ok(newUser);
    }

    // Endpoint para obtener un usuario por su username
    @GetMapping("/find")
    public ResponseEntity<?> findByUsername(@RequestParam String username) {
        Optional<Users> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint para obtener todos los usuarios
    @GetMapping("/")
    public ResponseEntity<List<Users>> getAllUsers() {
        List<Users> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // Endpoint para asignar un rol a un usuario
    @PostMapping("/{userId}/assign-role")
    public ResponseEntity<?> assignRole(@PathVariable Long userId, @RequestBody RoleAssignmentDto roleDto) {
        Optional<Users> user = userService.findById(userId);
        if (user.isPresent()) {
            userService.assignRole(user.get(), roleDto.getRoleName());
            return ResponseEntity.ok("Role assigned successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para actualizar un usuario
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody UserUpdateDto userDto) {
        Optional<Users> user = userService.findById(userId);
        if (user.isPresent()) {
            Users updatedUser = user.get();
            updatedUser.setUsername(userDto.getUsername());
            updatedUser.setEmail(userDto.getEmail());
            updatedUser.setPassword(userDto.getPassword());
            userService.updateUser(updatedUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar un usuario
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/{userId}/profile")
public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable Long userId) {
    UserProfileDto userProfile = userService.getUserProfile(userId);
    return ResponseEntity.ok(userProfile);
}

}
