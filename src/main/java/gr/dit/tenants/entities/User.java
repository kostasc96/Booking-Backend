package gr.dit.tenants.entities;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.*;
import gr.dit.tenants.view.Views;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import java.util.Set;


@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="userId",scope = User.class)
public class User{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Public.class)
    private Long userId;

	@NotBlank
	@Size(max = 20)
	@NaturalId
	@Column(unique = true, nullable = false, updatable = false, length = 20)
	@JsonView(Views.Public.class)
	private String username;

	@NotBlank
	@Size(max = 255)
	@Column(nullable = false, length = 255)
	@JsonIgnore
	private String password;

	@Size(max = 50)
	@Email
	@NaturalId
	@Column(unique = true, nullable = false, length = 50)
	@JsonView(Views.Public.class)
	private String email;

	@NotBlank(message = "Please provide a name")
	@Size(max = 50 , message = "Name cannot exceed 50 characters")
	@Column(nullable = false, length = 50)
	@JsonView(Views.Public.class)
    private String name;

	@NotBlank(message = "Please provide a surname")
	@Size(max = 50 , message = "Surname cannot exceed 50 characters")
	@Column(nullable = false, length = 100)
	@JsonView(Views.Public.class)
    private String surname;

	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@JsonView(Views.CurrentUser.class)
	private Set<Role> roles;

	@NotBlank(message = "Please provide a phone number")
	@Pattern(regexp = "^[0-9]*$", message = "Only numbers are valid characters")
	@Size(max = 20, message = "Mobile number cannot be more than 20 digits")
	@Column(nullable = false, length = 20)
	@JsonView(Views.Public.class)
    private String mobileNumber;

	//64KB MAX SIZE - To store n bytes in base64: 4*(n/3)
	@Size(max = 3145728, message = "Only images up to 64KB encoded in base64 are being accepted (87364 characters)")
	@JsonView(Views.Public.class)
    private String photo;

	@OneToMany(mappedBy = "renter")
	@JsonView(Views.CurrentUser.class)
	private Set<Reservation> reservations;

}
