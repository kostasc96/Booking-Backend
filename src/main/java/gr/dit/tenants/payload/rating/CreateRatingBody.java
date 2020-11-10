package gr.dit.tenants.payload.rating;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateRatingBody {

	@NotNull(message = "Please enter the id of the reservation you made")
	private Long reservationId;

	@NotNull(message = "Please enter a rating from 1 to 5")
	@Min(value = 1, message = "Rating cannot be less than 1")
	@Max(value = 5, message = "Rating cannot be more than 5")
	private Integer rating;

	@Size(max = 255, message = "Description cannot have more than 255 characters")
	private String description;
}