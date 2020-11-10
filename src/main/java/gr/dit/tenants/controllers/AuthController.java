package gr.dit.tenants.controllers;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonView;
import gr.dit.tenants.exceptions.BadRequestException;
import gr.dit.tenants.services.AuthService;
import gr.dit.tenants.services.UserService;
import gr.dit.tenants.view.ResponseStatus;
import gr.dit.tenants.view.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import gr.dit.tenants.entities.User;
import gr.dit.tenants.view.ResponseView;
//import gr.dit.tenants.payload.ApiResponse;
import gr.dit.tenants.view.authentication.LoginView;
import gr.dit.tenants.payload.authentication.SignInBody;
//import gr.dit.tenants.payload.ReservationRequest;
import gr.dit.tenants.payload.authentication.SignUpBody;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    @JsonView(Views.CurrentUser.class)
    public ResponseEntity<?> signIn(@Valid @RequestBody SignInBody body) {

        String jwt = authService.signIn(body);
        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.SUCCESSFUL_OPERATION,
                "Successful login", new LoginView(jwt)),
                HttpStatus.OK);
    }

    @PostMapping("/signup")
    @JsonView(Views.CurrentUser.class)
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpBody body) {

        User user = authService.signUp(body);
        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.SUCCESSFUL_OPERATION,
                "User registered successfully",user),
                HttpStatus.OK);

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseView> handleBadCredentialsException(BadCredentialsException ex)
    {

        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.FAILED_OPERATION,
                "Invalid credentials provided"),
                HttpStatus.BAD_REQUEST);
    }

}