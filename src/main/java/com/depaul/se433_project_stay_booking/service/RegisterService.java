package com.depaul.se433_project_stay_booking.service;

import com.depaul.se433_project_stay_booking.exception.UserAlreadyExistException;
import com.depaul.se433_project_stay_booking.model.Authority;
import com.depaul.se433_project_stay_booking.model.User;
import com.depaul.se433_project_stay_booking.model.UserRole;
import com.depaul.se433_project_stay_booking.repository.AuthorityRepository;
import com.depaul.se433_project_stay_booking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {
    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;

    }

    @Transactional(isolation = Isolation.SERIALIZABLE)// 保证如果成功就都成功，不会出现只有一个成功的情况
    public void add(User user, UserRole role) throws UserAlreadyExistException {
        if (userRepository.existsById(user.getUsername())) {
            throw new UserAlreadyExistException("User already exist");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), role.name()));
    }
}
