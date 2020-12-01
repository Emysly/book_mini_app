package com.emysilva.demo.controller;

import com.emysilva.demo.dto.LoginRequest;
import com.emysilva.demo.dto.LoginResponse;
import com.emysilva.demo.service.MyUserDetailsService;
import com.emysilva.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword())
            );
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @GetMapping("/")
    public ResponseEntity<?> home() {
        return ResponseEntity.ok(new LoginResponse("Welcome to home"));
    }

    @GetMapping("/user")
    public ResponseEntity<?> user() {
        return ResponseEntity.ok(new LoginResponse("Welcome to user"));
    }

    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        return ResponseEntity.ok(new LoginResponse("Welcome to admin"));
    }


}