package gr.dit.tenants.view.user;

import com.fasterxml.jackson.annotation.JsonView;
import gr.dit.tenants.entities.Rating;
import gr.dit.tenants.entities.Reservation;
import gr.dit.tenants.entities.User;
import gr.dit.tenants.view.Views;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserAllView {

    @JsonView(Views.CurrentUser.class)
    private User user;

    @JsonView(Views.CurrentUser.class)
    private List<Reservation> reservations;

    @JsonView(Views.CurrentUser.class)
    private List<Rating> ratings;
}
