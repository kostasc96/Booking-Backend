package gr.dit.tenants.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import gr.dit.tenants.entities.Rating;
import gr.dit.tenants.entities.Reservation;
import gr.dit.tenants.repositories.RatingRepository;
import gr.dit.tenants.repositories.ReservationRepository;
import gr.dit.tenants.view.ResponseStatus;
import gr.dit.tenants.view.ResponseView;
import gr.dit.tenants.view.Views;
import gr.dit.tenants.view.user.UserAllView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import gr.dit.tenants.repositories.UserRepository;
import gr.dit.tenants.security.CurrentUser;
import gr.dit.tenants.security.UserPrincipal;
import gr.dit.tenants.services.UserService;
import gr.dit.tenants.entities.User;
import gr.dit.tenants.payload.user.UserUpdateBody;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RatingRepository ratingRepository;

    static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/me")
    @JsonView(Views.CurrentUser.class)
    public ResponseEntity<?> getAllUserInfo(@CurrentUser UserPrincipal currentUser) {

        User user = userRepository.findById(currentUser.getId()).get();
        List<Reservation> reservations = reservationRepository.findAllByRenterUsername(currentUser.getUsername());
        List<Rating> ratings = ratingRepository.findAllByCreatorUsername(currentUser.getUsername());

        UserAllView view = new UserAllView();
        view.setUser(user);
        view.setReservations(reservations);
        view.setRatings(ratings);

        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.SUCCESSFUL_OPERATION,
                "Fetched all user details",view),
                HttpStatus.OK);
    }

    @GetMapping("me/reservations")
    @JsonView(Views.CurrentUser.class)
    public ResponseEntity<?> getUserReservations(@CurrentUser UserPrincipal currentUser)
    {
        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.SUCCESSFUL_OPERATION,
                "Reservations successfully fetched",
                reservationRepository.findAllByRenterUsername(currentUser.getUsername())),
                HttpStatus.OK);
    }

    @GetMapping("me/ratings")
    @JsonView(Views.CurrentUser.class)
    public ResponseEntity<?> getUserRatings(@CurrentUser UserPrincipal currentUser)
    {
        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.SUCCESSFUL_OPERATION,
                "Ratings successfully fetched",
                ratingRepository.findAllByCreatorUsername(currentUser.getUsername())),
                HttpStatus.OK);
    }

    @PutMapping("me/update")
    @JsonView(Views.CurrentUser.class)
    public ResponseEntity<?> updateUser(@CurrentUser UserPrincipal currentUser,
                                        @Valid @RequestBody UserUpdateBody updatedInfo) {

        User updatedUser = userService.updateUser(currentUser.getId(),updatedInfo);

        return new ResponseEntity<>(new ResponseView(
                ResponseStatus.SUCCESSFUL_OPERATION,
                "User successfully updated!", updatedUser),
                HttpStatus.OK);
    }

}