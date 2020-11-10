package gr.dit.tenants.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import gr.dit.tenants.entities.Reservation;
import gr.dit.tenants.security.CurrentUser;
import gr.dit.tenants.security.UserPrincipal;
import gr.dit.tenants.view.ResponseStatus;
import gr.dit.tenants.view.ResponseView;
import gr.dit.tenants.view.Views;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gr.dit.tenants.payload.reservation.CreateReservationBody;
import gr.dit.tenants.services.ReservationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
	
	static final Logger logger = LoggerFactory.getLogger(ReservationController.class);
	
	@Autowired
	ReservationService reservationService;

	@PostMapping("/create")
	@JsonView(Views.CurrentUser.class)
	 public ResponseEntity<?> createReservation(@CurrentUser UserPrincipal currentUser,
												@Valid @RequestBody CreateReservationBody body) {

		Reservation reservation = reservationService.createReservation(currentUser,body);

		return new ResponseEntity<>(new ResponseView(
				ResponseStatus.SUCCESSFUL_OPERATION,
				"You have successfully booked your room!",reservation)
				,HttpStatus.OK);
 	}

}
