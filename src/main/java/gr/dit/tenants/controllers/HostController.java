package gr.dit.tenants.controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import gr.dit.tenants.entities.User;
import gr.dit.tenants.repositories.RoomRepository;
import gr.dit.tenants.view.ResponseStatus;
import gr.dit.tenants.view.ResponseView;
import gr.dit.tenants.payload.room.CreateRoomBody;
import gr.dit.tenants.payload.room.UpdateRoomBody;
import gr.dit.tenants.services.HostService;
import gr.dit.tenants.view.Views;
import gr.dit.tenants.repositories.UserRepository;
import gr.dit.tenants.security.CurrentUser;
import gr.dit.tenants.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import gr.dit.tenants.entities.Room;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/host")
public class HostController {

	static final Logger logger = LoggerFactory.getLogger(RatingController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
	private HostService hostService;

	@GetMapping("/rooms/all")
	@JsonView(Views.CurrentUser.class)
	public ResponseEntity<?> getOwnedRooms(@CurrentUser UserPrincipal currentUser){
		User user = userRepository.findById(currentUser.getId()).get();
		List<Room> ownedRooms = roomRepository.findAllByOwnerUsername(user.getUsername());

		return new ResponseEntity<>(new ResponseView(
				ResponseStatus.SUCCESSFUL_OPERATION,
				"Successfully fetched all owned rooms",ownedRooms)
				,HttpStatus.OK);

	}

	@PostMapping("/rooms/create")
	@JsonView(Views.CurrentUser.class)
	public ResponseEntity<?> createRoom(@CurrentUser UserPrincipal currentUser,
										@Valid @RequestBody CreateRoomBody body) {

		Room room = hostService.createRoom(currentUser,body);

		return new ResponseEntity<>(new ResponseView(
				ResponseStatus.SUCCESSFUL_OPERATION,
				"Room was successfully created", room),
				HttpStatus.OK);
	}

	@PutMapping("/rooms/update")
	@PreAuthorize("@roomRepository.existsByRoomIdAndOwnerUsername(#body.getId() ,#currentUser.getUsername())")
	@JsonView(Views.CurrentUser.class)
	public ResponseEntity<?> updateRoom(@CurrentUser UserPrincipal currentUser,
										@Valid @RequestBody UpdateRoomBody body) {

		Room updatedRoom = hostService.updateRoom(body);

		return new ResponseEntity<>(new ResponseView(
				ResponseStatus.SUCCESSFUL_OPERATION,
				"Room was successfully updated", updatedRoom),
				HttpStatus.OK);
	}

	@DeleteMapping("/rooms/delete")
	@PreAuthorize("@roomRepository.existsByRoomIdAndOwnerUsername(#id ,#currentUser.getUsername())")
	@JsonView(Views.CurrentUser.class)
	public ResponseEntity<?> deleteRoom(@CurrentUser UserPrincipal currentUser,
										@RequestParam(value = "id") Long id) {

		roomRepository.deleteById(id);
		return new ResponseEntity<>(new ResponseView(
				ResponseStatus.SUCCESSFUL_OPERATION,
				"Room successfully deleted")
				,HttpStatus.OK);
	}
}