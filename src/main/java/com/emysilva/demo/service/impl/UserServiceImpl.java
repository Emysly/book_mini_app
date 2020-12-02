package com.emysilva.demo.service.impl;

import com.emysilva.demo.dto.SignupRequest;
import com.emysilva.demo.exception.UserExistException;
import com.emysilva.demo.model.ERole;
import com.emysilva.demo.model.Role;
import com.emysilva.demo.model.User;
import com.emysilva.demo.repository.RoleRepository;
import com.emysilva.demo.repository.UserRepository;
import com.emysilva.demo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, UserService userService, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public void registerUser(SignupRequest signUpRequest) {

        if (userService.existsByUsername(signUpRequest.getUsername())) {
            throw new UserExistException("Error: Username is already taken!");
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            throw new UserExistException("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName( ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                        if (adminRole.isEmpty()) {
                            Role role1 = new Role(ERole.ROLE_ADMIN);
                            roleRepository.save(role1);
                            adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);
                            roles.add(adminRole.orElseThrow(() -> new RuntimeException("Role not found")));
                        }
                        roles.add(adminRole.orElseThrow(() -> new RuntimeException("Role not found")));

                        break;
                    case "pub":
                        Optional<Role> modRole = roleRepository.findByName(ERole.ROLE_PUBLISHER);
                        if (modRole.isEmpty()) {
                            Role role1 = new Role(ERole.ROLE_PUBLISHER);
                            roleRepository.save(role1);
                            modRole = roleRepository.findByName(ERole.ROLE_PUBLISHER);
                            roles.add(modRole.orElseThrow(() -> new RuntimeException("Role not found")));
                        }
                        roles.add(modRole.orElseThrow(() -> new RuntimeException("Role not found")));

                        break;
                    default:
                        Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
                        if (userRole.isEmpty()) {
                            Role role1 = new Role(ERole.ROLE_USER);
                            roleRepository.save(role1);
                            userRole = roleRepository.findByName(ERole.ROLE_USER);
                            roles.add(userRole.orElseThrow(() -> new RuntimeException("Role not found")));
                        }
                        roles.add(userRole.orElseThrow(() -> new RuntimeException("Role not found")));
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
