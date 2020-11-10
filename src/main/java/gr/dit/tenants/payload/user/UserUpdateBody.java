package gr.dit.tenants.payload.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserUpdateBody
{
	@Size(max = 50 , message = "Name cannot exceed 50 characters")
	private String name;

	@Size(max = 50 , message = "Surname cannot exceed 50 characters")
	private String surname;

	private Boolean isHost;

	@Size(max = 50, message = "Email cannot be more than 50 characters")
	@Email(message = "Please provide a valid e-mail address")
	private String email;

	@Pattern(regexp = "^[0-9]*$", message = "Only numbers are valid characters")
	@Size(max = 20, message = "Mobile number cannot be more than 20 digits")
	private String mobileNumber;

	@Size(max = 3145728, message = "Only images up to 64KB encoded in base64 are being accepted (87364 characters)")
	private String photo;
}
