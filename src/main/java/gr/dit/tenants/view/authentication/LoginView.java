package gr.dit.tenants.view.authentication;

import com.fasterxml.jackson.annotation.JsonView;
import gr.dit.tenants.view.Views;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginView {

    @JsonView(Views.CurrentUser.class)
    private String accessToken;
}
