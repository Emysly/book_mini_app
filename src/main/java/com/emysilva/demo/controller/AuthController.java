package com.emysilva.demo.controller;

import com.emysilva.demo.config.jwt.JwtUtils;
import com.emysilva.demo.dto.JwtResponse;
import com.emysilva.demo.dto.LoginRequest;
import com.emysilva.demo.dto.MessageResponse;
import com.emysilva.demo.dto.SignupRequest;
import com.emysilva.demo.model.ERole;
import com.emysilva.demo.model.Role;
import com.emysilva.demo.model.User;
import com.emysilva.demo.repository.RoleRepository;
import com.emysilva.demo.repository.UserRepository;
import com.emysilva.demo.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
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
						roles.add(adminRole.get());
					}
					roles.add(adminRole.get());

					break;
				case "mod":
					Optional<Role> modRole = roleRepository.findByName(ERole.ROLE_MODERATOR);
					if (modRole.isEmpty()) {
						Role role1 = new Role(ERole.ROLE_MODERATOR);
						roleRepository.save(role1);
						modRole = roleRepository.findByName(ERole.ROLE_MODERATOR);
						roles.add(modRole.get());
					}
					roles.add(modRole.get());

					break;
				default:
					Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
					if (userRole.isEmpty()) {
						Role role1 = new Role(ERole.ROLE_USER);
						roleRepository.save(role1);
						userRole = roleRepository.findByName(ERole.ROLE_USER);
						roles.add(userRole.get());
					}
					roles.add(userRole.get());
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}