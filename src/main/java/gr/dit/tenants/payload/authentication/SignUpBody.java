package gr.dit.tenants.payload.authentication;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


@Getter
@Setter
public class SignUpBody {

    @NotBlank(message = "Username is required")
    @Size(max = 20, message = "Username cannot exceed 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min=8, max = 50 , message = "Please provide a password between 8 and 50 characters")
    private String password;

    @NotBlank(message = "Please provide an e-mail address")
    @Size(max = 50, message = "Email cannot be more than 50 characters")
    @Email(message = "Please provide a valid e-mail address")
    private String email;

    @NotBlank(message = "Please provide a name")
    @Size(max = 50 , message = "Name cannot exceed 50 characters")
    private String name;

    @NotBlank(message = "Please provide a surname")
    @Size(max = 50 , message = "Surname cannot exceed 50 characters")
    private String surname;

    @NotNull(message = "Please provide if user will or not also be a host")
    private Boolean isHost;

    @NotBlank(message = "Please provide a phone number")
    @Pattern(regexp = "^[0-9]*$", message = "Only numbers are valid characters")
    @Size(max = 20, message = "Mobile number cannot be more than 20 digits")
    private String mobileNumber;

    @Size(max = 87384, message = "Only images up to 64KB encoded in base64 are being accepted (87364 characters)")
    private String photo;

}
