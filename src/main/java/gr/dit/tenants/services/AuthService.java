package gr.dit.tenants.services;

import gr.dit.tenants.entities.Role;
import gr.dit.tenants.entities.User;
import gr.dit.tenants.exceptions.BadRequestException;
import gr.dit.tenants.payload.authentication.SignInBody;
import gr.dit.tenants.payload.authentication.SignUpBody;
import gr.dit.tenants.repositories.UserRepository;
import gr.dit.tenants.security.JwtTokenProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    public User signUp(SignUpBody body) {

        if(userRepository.existsByUsername(body.getUsername()))
            throw new BadRequestException("Username is already taken!");

        if(userRepository.existsByEmail(body.getEmail()))
            throw new BadRequestException("Email Address already in use! Maybe you already have an account.");

        User user = new User();
        BeanUtils.copyProperties(body,user,"password","isHost"); //Ignore password as it must be encoded
        user.setPassword(passwordEncoder.encode(body.getPassword()));

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        if(body.getIsHost() == true)
            roles.add(Role.HOST);

        user.setRoles(roles);
        return userRepository.saveAndFlush(user);
    }

    public String signIn(SignInBody body) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        body.getUsername(),
                        body.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.generateToken(authentication);
    }
}
