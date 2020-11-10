package gr.dit.tenants.payload.authentication;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignInBody {
    @NotBlank(message = "Username is required")
    @Size(max = 20)
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min=8, max = 50 , message = "Please provide a password between 8 and 50 characters")
    private String password;
}
