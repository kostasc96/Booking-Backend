package gr.dit.tenants.services;

import gr.dit.tenants.entities.Reservation;
import gr.dit.tenants.entities.User;
import gr.dit.tenants.exceptions.BadRequestException;
import gr.dit.tenants.exceptions.ResourceNotFoundException;
import gr.dit.tenants.repositories.ReservationRepository;
import gr.dit.tenants.repositories.UserRepository;
import gr.dit.tenants.security.CurrentUser;
import gr.dit.tenants.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import gr.dit.tenants.entities.Rating;
import gr.dit.tenants.payload.rating.CreateRatingBody;
import gr.dit.tenants.repositories.RatingRepository;


import java.time.Instant;
import java.util.Date;

@Service
public class RatingService {
	
	static final Logger logger = LoggerFactory.getLogger(RatingService.class);

	private final UserRepository userRepository;
	private final ReservationRepository reservationRepository;
	private final RatingRepository ratingRepository;

	public RatingService(UserRepository userRepository, ReservationRepository reservationRepository, RatingRepository ratingRepository) {
		this.userRepository = userRepository;
		this.reservationRepository = reservationRepository;
		this.ratingRepository = ratingRepository;
	}

	public Rating createRating(@CurrentUser UserPrincipal currentUser, CreateRatingBody body) {

		User user = userRepository.getOne(currentUser.getId());
		Reservation reservation = reservationRepository.findById(body.getReservationId())
				.orElseThrow(() -> new ResourceNotFoundException("The specified reservation does not exist"));

		Date now = Date.from(Instant.now());
		if(reservation.getToDate().after(now))
			throw new BadRequestException("Reservation has not finished yet, so a rating cannot be made yet.");

		Rating rating = new Rating();
		BeanUtils.copyProperties(body,rating);
		rating.setCreator(user);
		rating.setReservation(reservation);
		rating.setRoom(reservation.getRoom());

		return ratingRepository.saveAndFlush(rating);
	}
}
