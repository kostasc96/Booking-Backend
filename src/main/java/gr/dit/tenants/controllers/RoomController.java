package gr.dit.tenants.controllers;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import gr.dit.tenants.entities.Rating;
import gr.dit.tenants.entities.Room;
import gr.dit.tenants.exceptions.ResourceNotFoundException;
import gr.dit.tenants.payload.reservation.CreateReservationBody;
import gr.dit.tenants.payload.room.SearchAvailableRoomsBody;
import gr.dit.tenants.repositories.RatingRepository;
import gr.dit.tenants.view.ResponseStatus;
import gr.dit.tenants.view.ResponseView;
import gr.dit.tenants.view.Views;
import gr.dit.tenants.repositories.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {
	
	static final Logger logger = LoggerFactory.getLogger(RoomController.class);

	@Autowired
    private RoomRepository roomRepository;

	@Autowired
	private RatingRepository ratingRepository;


	@GetMapping("/all")
	@JsonView(Views.Public.class)
	public ResponseEntity<?> getAllRooms() {
		List<Room> rooms = roomRepository.findAll();
		return new ResponseEntity<>(new ResponseView(
				ResponseStatus.SUCCESSFUL_OPERATION,
				"Successfully fetched all available rooms", rooms),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> getRoom(@PathVariable(value = "id") Long id) {
    	Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Room with id: " + id + " doesn't exist."));

        return new ResponseEntity<>(room,HttpStatus.OK);

    }

	@GetMapping("/ratings/{id}")
	@JsonView(Views.Public.class)
	public ResponseEntity<?> getRoomRatings(@PathVariable("id") Long id)
	{
		Room room = roomRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Room with id: " + id + "does not exist"));
		List<Rating> ratings = ratingRepository.findAllByRoom(room);
		return new ResponseEntity<>(ratings,HttpStatus.OK);
	}

	@GetMapping("/availability/all")
	@JsonView(Views.Public.class)
	public ResponseEntity<?> availableRooms(@RequestParam(name="city") String city,
											@RequestParam(name="country") String country,
											@RequestParam(name="fromDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate,
											@RequestParam(name="toDate")   @DateTimeFormat(pattern = "yyyy-MM-dd") Date toDate,
											@RequestParam(name="people") Integer people) {


		List<Room> availableRooms = roomRepository.findAvailableRooms(city, country, people, fromDate, toDate);
		return new ResponseEntity<>(new ResponseView(
				ResponseStatus.SUCCESSFUL_OPERATION,
				"Successfully fetched all available rooms", availableRooms),
				HttpStatus.OK);
	}


	@GetMapping("availability/check")
	@JsonView(Views.Public.class)
	@Transactional
	public ResponseEntity<?> checkRoomAvailability(@Valid CreateReservationBody body)
	{
		Room room = roomRepository.isRoomAvailable(body.getRoomId(),body.getFromDate(),body.getToDate(), body.getPersons());
		boolean status = room == null;
		return new ResponseEntity<>(new ResponseView(
				ResponseStatus.SUCCESSFUL_OPERATION,
				"Availability successfully checked",status),
				HttpStatus.OK);
	}
}
