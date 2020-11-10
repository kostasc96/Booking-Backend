package gr.dit.tenants.repositories;

import java.util.List;

import gr.dit.tenants.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import gr.dit.tenants.entities.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

	List<Rating> findAllByCreatorUsername(String Username);
	List<Rating> findAllByRoom(Room room);
}
