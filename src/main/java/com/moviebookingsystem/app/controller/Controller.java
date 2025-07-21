package com.moviebookingsystem.app.controller;

import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.LoginUserRequestDTO;
import com.moviebookingsystem.app.model.User;
import com.moviebookingsystem.app.security.JwtUtil;
import com.moviebookingsystem.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
class Controller{

	@GetMapping("/")
	public String getHome(){
		return "Welcome!!! Back!! ";
	}


}