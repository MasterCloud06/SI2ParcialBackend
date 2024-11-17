package com.example.historiaclinica.service;

import com.example.historiaclinica.dto.UserProfileDto;
import com.example.historiaclinica.model.Role;
import com.example.historiaclinica.model.RoleName;
import com.example.historiaclinica.model.Users;
import com.example.historiaclinica.repository.RoleRepository;
import com.example.historiaclinica.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
// Importación corregida
import org.springframework.security.core.userdetails.UsernameNotFoundException;
// Cambiado a constructor
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // Inyección por constructor
    
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con nombre de usuario: " + username));
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                        .collect(Collectors.toList())
        );
    }

    public Users registerUser(Users user, Set<Role> roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<Users> findById(Long id) {
        return userRepository.findById(id);
    }

    public void assignRole(Users user, RoleName roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public Users updateUser(Users user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Role getRoleByName(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
    }

    // Método para obtener todos los usuarios
    public List<Users> findAllUsers() {
        return userRepository.findAll(); // Devuelve todos los usuarios
    }

    // Método para obtener el perfil de un usuario
    public UserProfileDto getUserProfile(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + userId));

        // Convertir Users a UserProfileDto
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setUsername(user.getUsername());
        userProfileDto.setEmail(user.getEmail());
        userProfileDto.setRoles(user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet()));

        return userProfileDto;
    }
}
