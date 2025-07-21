package com.moviebookingsystem.app.services;

import com.moviebookingsystem.app.constants.Role;
import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.LoginUserRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.RegisterUserRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.UserIdRequestDTO;
import com.moviebookingsystem.app.dto.requestDTO.userRequestDTO.UsernameRequestDTO;
import com.moviebookingsystem.app.exceptions.UnauthorizedException;
import com.moviebookingsystem.app.exceptions.UserAlreadyRegisteredException;
import com.moviebookingsystem.app.exceptions.UserNotFoundException;
import com.moviebookingsystem.app.exceptions.UsernameAndPasswordIncorrectException;
import com.moviebookingsystem.app.model.Booking;
import com.moviebookingsystem.app.model.User;
import com.moviebookingsystem.app.repository.UserRepository;
import com.moviebookingsystem.app.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }


    public User getUserByIdHelper(Integer userId) throws UserNotFoundException {
        return  userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public User getUserByUsernameHelper(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    //if its admin then return admin else return username
    public User loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            return getUserByUsernameHelper(username);
        }
        catch (UserNotFoundException e){
            log.warn(e.getMessage());
            throw new UserNotFoundException("No User Logged in");
        }
    }


    public User getUserById(UserIdRequestDTO userIdRequest) throws UserNotFoundException {
        return getUserByIdHelper(userIdRequest.getUserId());
    }

    public User getUserByUsername(UsernameRequestDTO usernameRequest) throws UserNotFoundException,UnauthorizedException {
        User loggedInUser = loggedInUser();
        User requestedUser = getUserByUsernameHelper(usernameRequest.getUsername());
        if(loggedInUser.getRole().equals(Role.ADMIN) || requestedUser.getUsername().equals(usernameRequest.getUsername())){
            return getUserByUsernameHelper(usernameRequest.getUsername());
        }
        else {
            throw new UnauthorizedException("Unauthorized access");
        }
    }

    public List<User> getAllUser() throws UnauthorizedException {
        User loggedInUser = loggedInUser();
        if(loggedInUser.getRole().equals(Role.ADMIN)) {
            return userRepository.findAll();
        }
        else {
            throw new UnauthorizedException("Unauthorized access");
        }
    }

    //admin can create admin or else user as user
    public String registerUser(RegisterUserRequestDTO userRequest) throws UnauthorizedException, UserAlreadyRegisteredException {
        if(userRepository.existsByUsername(userRequest.getUsername())) {
            throw new UserAlreadyRegisteredException("User: " + userRequest.getUsername() + " already registered");
        }
        User newUser = new User();
        newUser.setUsername(userRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        newUser.setFirstname(userRequest.getFirstname());
        newUser.setLastname(userRequest.getLastname());

        if(userRequest.getRole().equals(Role.ADMIN)){
            try{
                if(loggedInUser().getRole().equals(Role.ADMIN)) {
                    newUser.setRole(Role.ADMIN);
                }
            }
            catch (UserNotFoundException e){
                log.warn(e.getMessage());
                throw new UnauthorizedException("You are not authorized to register new admin");
            }
        }

        newUser.setUserBookings(new ArrayList<>());
        userRepository.save(newUser);

        return authenticate(userRequest.getUsername(),userRequest.getPassword());

    }

    //same username or delete by an admin
    public void deleteUser(UserIdRequestDTO userIdRequest) throws UserNotFoundException,UnauthorizedException{
        User user = getUserById(userIdRequest);
        User loggedInUser = loggedInUser();
        if(loggedInUser.getRole().equals(Role.ADMIN) || loggedInUser.getUsername().equals(user.getUsername())) {
            userRepository.delete(user);
        }
        else {
            throw new UnauthorizedException("You are not authorized to delete user");
        }
    }

    public List<Booking> getBookings(UserIdRequestDTO userIdRequest) throws UserNotFoundException,UnauthorizedException {
        User user =  getUserById(userIdRequest);
        User loggedInUser = loggedInUser();
        if(loggedInUser.getRole().equals(Role.ADMIN) || loggedInUser.getUsername().equals(user.getUsername())) {
            return user.getUserBookings();
        }
        else {
            throw new UnauthorizedException("You are not authorized to view bookings");
        }
    }

    public String authenticate(String username,String password) throws UnauthorizedException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails);
    }
}
