package gr.dit.tenants.services;

import gr.dit.tenants.entities.Room;
import gr.dit.tenants.exceptions.BadRequestException;
import gr.dit.tenants.exceptions.ResourceNotFoundException;
import gr.dit.tenants.security.CurrentUser;
import gr.dit.tenants.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import gr.dit.tenants.entities.Reservation;
import gr.dit.tenants.entities.User;
import gr.dit.tenants.payload.reservation.CreateReservationBody;
import gr.dit.tenants.repositories.ReservationRepository;
import gr.dit.tenants.repositories.RoomRepository;
import gr.dit.tenants.repositories.UserRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class ReservationService {
	
	static final Logger logger = LoggerFactory.getLogger(ReservationService.class);
	
	private final ReservationRepository reservationRepository;
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;
	
	public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository, UserRepository userRepository) {
		this.reservationRepository = reservationRepository;
		this.roomRepository = roomRepository;
		this.userRepository = userRepository;
	}
	

	@Transactional
	public Reservation createReservation(@CurrentUser UserPrincipal currentUser,
										 CreateReservationBody body) {

		User user = userRepository.findByUsername(currentUser.getUsername()).get();
		Room room = roomRepository.findById(body.getRoomId()).orElseThrow(() ->
				new ResourceNotFoundException("Room with id: " + body.getRoomId() + " doesn't exist."));

		if(room.getOwner().equals(user))
			throw new BadRequestException("You cannot rent your own room");

		if(roomRepository.isRoomAvailable(room.getRoomId(), body.getFromDate(),body.getToDate(),body.getPersons()) == null)
			throw new BadRequestException("This room is not available for your criteria");

		Reservation reservation = new Reservation();
		BeanUtils.copyProperties(body,reservation);
		reservation.setRenter(user);
		reservation.setRoom(room);

		BigDecimal roomPrice = room.getPrice();
		BigDecimal extraPeople = room.getExtraCostPerPerson().multiply(BigDecimal.valueOf(body.getPersons() - 1));
		BigDecimal totalPrice = roomPrice.add(extraPeople);

		reservation.setPrice(totalPrice);

		return reservationRepository.saveAndFlush(reservation);

	}

}
