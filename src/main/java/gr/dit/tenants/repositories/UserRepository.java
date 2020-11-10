package gr.dit.tenants.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.dit.tenants.entities.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
    Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    
}
