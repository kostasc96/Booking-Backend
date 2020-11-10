package gr.dit.tenants.payload.reservation;

import gr.dit.tenants.entities.validation.DatePeriod;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@DatePeriod(startingDateField = "fromDate", endingDateField = "toDate")
public class CreateReservationBody {

	@NotNull(message = "Please provide the id of the room you would like to book")
	private Long roomId;

	@NotNull(message = "Persons field missing")
	@Min(value = 1, message = "Minimum of 1 person is required")
	private Integer persons;

	/* @FutureOrPresent annotations ARE disabled on purpose because ratings cannot be easily tested */
	@NotNull(message = "Please provide the from date")
	//@FutureOrPresent(message = "From date must be present or future")
	private Date fromDate;

	@NotNull(message = "Please provide the to date")
	//@FutureOrPresent(message = "To date must be present or future")
	private Date toDate;

}
