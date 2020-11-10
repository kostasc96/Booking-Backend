package gr.dit.tenants.payload.room;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
public class SearchAvailableRoomsBody {

    @NotBlank(message = "Please provide a city")
    private String city;

    @NotBlank(message = "Please provide a country")
    private String country;

    @NotNull(message = "Please enter number of people")
    @Min(value = 1, message = "At least 1 person must stay")
    private Integer people;

    @NotNull(message = "Please provide a from date")
    private Date fromDate;

    @NotNull(message = "Please provide a to date")
    private Date toDate;
}
