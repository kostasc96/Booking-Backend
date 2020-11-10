package gr.dit.tenants.services;

import gr.dit.tenants.entities.User;
import gr.dit.tenants.exceptions.ResourceNotFoundException;
import gr.dit.tenants.payload.room.CreateRoomBody;
import gr.dit.tenants.payload.room.UpdateRoomBody;
import gr.dit.tenants.security.CurrentUser;
import gr.dit.tenants.security.UserPrincipal;
import gr.dit.tenants.util.CustomBeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import gr.dit.tenants.entities.Room;
import gr.dit.tenants.repositories.RatingRepository;
import gr.dit.tenants.repositories.RoomRepository;
import gr.dit.tenants.repositories.UserRepository;

@Service
public class HostService {
	
	static final Logger logger = LoggerFactory.getLogger(HostService.class);
	
	private final RoomRepository roomRepository;
	private final UserRepository userRepository;

	public HostService(RoomRepository roomRepository, UserRepository userRepository, RatingRepository ratingRepository) {
	  this.roomRepository = roomRepository;
	  this.userRepository = userRepository;
	}

	public Room createRoom(@CurrentUser UserPrincipal currentUser, CreateRoomBody body) {

		User owner = userRepository.findById(currentUser.getId()).get();

		Room room = new Room();
		room.setOwner(owner); //Set the current user as the room's owner
		BeanUtils.copyProperties(body,room);
		return roomRepository.saveAndFlush(room);
	}


	public Room updateRoom(UpdateRoomBody body) {

		Room room = roomRepository.findById(body.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Room with id: " + body.getId() + " doesn't exist."));

		CustomBeanUtils.copyNonNullProperties(body,room);
		return roomRepository.saveAndFlush(room);
	}

}
