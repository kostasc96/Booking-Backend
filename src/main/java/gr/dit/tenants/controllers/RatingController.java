package gr.dit.tenants.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import gr.dit.tenants.entities.Rating;
import gr.dit.tenants.view.ResponseStatus;
import gr.dit.tenants.view.ResponseView;
import gr.dit.tenants.repositories.RatingRepository;
import gr.dit.tenants.security.CurrentUser;
import gr.dit.tenants.security.UserPrincipal;
import gr.dit.tenants.view.Views;;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import gr.dit.tenants.payload.rating.CreateRatingBody;
import gr.dit.tenants.services.RatingService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	static final Logger logger = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	private RatingService ratingService;


	 @PostMapping("/create")
	 @PreAuthorize("@reservationRepository.findReservationOfRenterByUsername(#body.getReservationId(), #currentUser.getUsername()) != null")
	 @JsonView(Views.CurrentUser.class)
	 public ResponseEntity<?> createRating(@CurrentUser UserPrincipal currentUser,
										   @Valid @RequestBody CreateRatingBody body) {

		 Rating rating = ratingService.createRating(currentUser,body);

		 return new ResponseEntity<>(new ResponseView(ResponseStatus.SUCCESSFUL_OPERATION,
				 "Rating successfully created!",rating),
				 HttpStatus.OK);
	 }

}