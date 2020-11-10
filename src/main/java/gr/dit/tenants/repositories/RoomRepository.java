package gr.dit.tenants.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.dit.tenants.entities.Room;

import javax.persistence.LockModeType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{

	List<Room> findAllByOwnerUsername(String username);
	Boolean existsByRoomIdAndOwnerUsername(Long id,String username);


	@Query("SELECT r FROM Room r " +
			"WHERE r.city = :city " +
			"AND r.country = :country " +
			"AND r.peopleNumber >= :peopleNumber " +
			"AND (r.beginDate <= :beginDate AND r.endDate >= :endDate)" +
			"AND r NOT IN " +
			"(" +
				"SELECT res.room " +
				"FROM Reservation res " +
				"WHERE (res.fromDate <= :beginDate AND res.toDate >= :beginDate) " +
				"OR (res.fromDate < :endDate AND res.toDate >= :endDate) " +
				"OR (res.fromDate >= :beginDate AND res.fromDate <= :endDate) " +
			")")
	List<Room> findAvailableRooms(@Param("city") String city,
								  @Param("country") String country,
								  @Param("peopleNumber") Integer peopleNumber,
								  @Param("beginDate") Date beginDate,
								  @Param("endDate") Date endDate);


	@Query("SELECT r FROM Room r " +
			"WHERE r.roomId = :roomId " +
			"AND (r.beginDate <= :beginDate AND r.endDate >= :endDate) " +
			"AND r.peopleNumber >= :peopleNumber " +
			"AND r NOT IN " +
			"(" +
			"SELECT res.room " +
			"FROM Reservation res " +
			"WHERE (res.fromDate <= :beginDate AND res.toDate >= :beginDate) " +
			"OR (res.fromDate < :endDate AND res.toDate >= :endDate) " +
			"OR (res.fromDate >= :beginDate AND res.fromDate <= :endDate) " +
			")")
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	Room isRoomAvailable(@Param("roomId") Long roomId,
						 @Param("beginDate") Date beginDate,
						 @Param("endDate") Date endDate,
						 @Param("peopleNumber") Integer peopleNumber);

}
