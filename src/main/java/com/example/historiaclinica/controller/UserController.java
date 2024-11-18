package com.example.historiaclinica.controller;

import com.example.historiaclinica.dto.RoleAssignmentDto;
import com.example.historiaclinica.dto.UserRegistrationDto;
import com.example.historiaclinica.dto.UserUpdateDto;
import com.example.historiaclinica.model.Medico;
import com.example.historiaclinica.model.Role;
import com.example.historiaclinica.model.RoleName;
import com.example.historiaclinica.model.Users;
import com.example.historiaclinica.service.MedicoService;
import com.example.historiaclinica.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final MedicoService medicoService;

    
    public UserController(UserService userService, MedicoService medicoService) {
        this.userService = userService;
        this.medicoService = medicoService;
    }

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
    @PostMapping("/{userId:\\d+}/assign-role")
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
    @PutMapping("/{userId:\\d+}")
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
    @DeleteMapping("/{userId:\\d+}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully");
    }

    // Endpoint para obtener el perfil de un usuario por su ID numérico
    @GetMapping("/{userId:\\d+}/profile")
    public ResponseEntity<Users> getUserProfile(@PathVariable Long userId, Authentication authentication) {
        // Opcional: Verificar si el usuario tiene permiso para ver este perfil
        Users user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(user);
    }

    // Nuevo endpoint para obtener usuarios por rol (alfabético)
    @PreAuthorize("hasRole('ADMIN')") // Opcional: Restringir acceso a administradores
    @GetMapping("/{roleName:[A-Za-z]+}")
    public ResponseEntity<?> getUsersByRoleName(@PathVariable String roleName) {
        RoleName role;
        try {
            role = RoleName.valueOf(roleName.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Rol no válido: " + roleName);
        }

        List<Users> users = userService.findUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{roleName:[A-Za-z]+}/especialidades")
public ResponseEntity<?> getMedicosWithEspecialidades(@PathVariable String roleName) {
    // Verifica si el rol es 'MEDICO'
    if (!roleName.equalsIgnoreCase("MEDICO")) {
        return ResponseEntity.badRequest().body("Solo se pueden obtener especialidades de médicos.");
    }

    // Obtén los médicos con sus especialidades
    List<Medico> medicos = medicoService.obtenerTodosLosMedicos();
    if (medicos.isEmpty()) {
        return ResponseEntity.noContent().build();
    }

    // Devuelve la lista de médicos con sus especialidades
    return ResponseEntity.ok(medicos);
}
@GetMapping("/{roleName:[A-Za-z]+}/especialidades/{especialidadId}")
public ResponseEntity<?> getMedicosByEspecialidad(@PathVariable String roleName, @PathVariable Long especialidadId) {
    // Verifica si el rol es 'MEDICO'
    if (!roleName.equalsIgnoreCase("MEDICO")) {
        return ResponseEntity.badRequest().body("Solo se pueden obtener médicos con especialidades.");
    }

    // Obtiene los médicos con la especialidad específica
    List<Medico> medicos = medicoService.obtenerMedicosPorEspecialidad(especialidadId);
    if (medicos.isEmpty()) {
        return ResponseEntity.noContent().build();
    }

    // Devuelve la lista de médicos con la especialidad
    return ResponseEntity.ok(medicos);
}

}
