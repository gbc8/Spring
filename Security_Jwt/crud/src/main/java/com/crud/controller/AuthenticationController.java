package com.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crud.model.AuthenticationDTO;
import com.crud.model.LoginResponseDTO;
import com.crud.model.User;
import com.crud.model.registerDTO;
import com.crud.repository.UserRepository;
import com.crud.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private TokenService tokenService;
	
	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.tokenService = tokenService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid registerDTO data){
		if(this.userRepository.findByUsername(data.username()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.username(), encryptedPassword, data.role());
		
		this.userRepository.save(newUser);
		return ResponseEntity.ok().build();
	}
}
