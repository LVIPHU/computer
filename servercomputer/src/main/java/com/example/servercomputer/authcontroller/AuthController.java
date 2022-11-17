package com.example.servercomputer.authcontroller;

import com.example.servercomputer.dto.LoginRequest;
import com.example.servercomputer.dto.SignupRequest;
import com.example.servercomputer.entity.Role;
import com.example.servercomputer.entity.User;
import com.example.servercomputer.repository.RoleRepository;
import com.example.servercomputer.repository.UserRepository;
import com.example.servercomputer.response.JwtTokenResponse;
import com.example.servercomputer.response.MessageResponse;
import com.example.servercomputer.security.jwt.AuthTokenFilter;
import com.example.servercomputer.security.jwt.JwtUtils;
import com.example.servercomputer.service.impl.MyUserDetails;
import com.example.servercomputer.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


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
    private AuthTokenFilter jwtFilter;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser( @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new JwtTokenResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    roles));
        }catch (Exception e){
            return new ResponseEntity<>(new MessageResponse("User login no success!"), HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        User u = new User(signupRequest.getEmail(),
                encoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRoles();
        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByRoleName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("admin".equals(role.toString())) {
                    Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);
                } else if ("manager".equals(role.toString())) {
                    Role modRole = roleRepository.findByRoleName("ROLE_MANAGER")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                } else {
                    Role userRole = roleRepository.findByRoleName("ROLE_USER")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }
        u.setRoles(roles);
        userRepository.save(u);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @PostMapping("/changePassword")
    ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap){
        BCryptPasswordEncoder encoder1=new BCryptPasswordEncoder();
        try {
            User userObj=userRepository.findByEmail(jwtFilter.getCurrentUser()).get();
            if (!userObj.equals(null)){
                if (encoder1.matches(requestMap.get("oldPassword"), userObj.getPassword())){
                    userObj.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
                    userRepository.save(userObj);
                    return ResponseUtils.getResponseEntity("Password update success", HttpStatus.OK);
                }
                return ResponseUtils.getResponseEntity("Incorrect old password", HttpStatus.BAD_REQUEST);
            }
            return ResponseUtils.getResponseEntity("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseUtils.getResponseEntity("something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}