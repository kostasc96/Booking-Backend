package gr.dit.tenants.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.dit.tenants.entities.Reservation;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public interface ReservationRepository extends JpaRepository<Reservation, Long>{
    List<Reservation> findAllByRenterUsername(String username);

    @Query("SELECT res FROM Reservation res " +
            "WHERE res.reservationId = :id " +
            "AND res.renter.username = :username")
    Reservation findReservationOfRenterByUsername(Long id, String username);
}
