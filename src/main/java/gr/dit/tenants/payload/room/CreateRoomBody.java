package gr.dit.tenants.payload.room;

import gr.dit.tenants.entities.validation.DatePeriod;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@DatePeriod(startingDateField = "beginDate",endingDateField = "endDate")
public class CreateRoomBody {

    @NotBlank(message = "Please provide a name for the room")
    @Size(max = 50, message = "Room name cannot exceed 50 characters")
    private String name;

    @NotBlank(message = "Please provide a country")
    @Size(max = 50, message = "Country's name cannot exceed 50 characters")
    private String country;

    @NotBlank(message = "Please provide a city")
    @Size(max = 50, message = "City's name cannot exceed 50 characters")
    private String city;

    @NotBlank(message = "Please provide a address")
    @Size(max = 50, message = "Address' name cannot exceed 50 characters")
    private String address;

    @NotNull(message = "Please provide a begin date")
    //@FutureOrPresent(message = "Begin date must be present or future")
    private Date beginDate;

    @NotNull(message = "Please provide an end date")
    //@FutureOrPresent(message = "End date must be present or future")
    private Date endDate;

    @NotNull(message = "Please provide the maximum number of people than can book this room")
    @Min(value = 1, message = "At least 1 person must be able to stay")
    private Integer peopleNumber;

    @NotNull(message = "Please provide the maximum number of people than can book this room")
    @DecimalMin(value = "0", message = "Price cannot be negative")
    private BigDecimal price;

    @NotNull(message = "Please provide the extra cost per person")
    @DecimalMin(value = "0", message = "Extra cost per person cannot be negative")
    private BigDecimal extraCostPerPerson;

    @NotNull(message = "Please provide the room type")
    @Size(max = 50, message = "Room type description cannot exceed 50 characters")
    private String roomType;

    @Size(max = 255, message = "Rules description cannot exceed 255 characters")
    private String rules;

    @Size(max = 255, message = "Room description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Please provide the m2 of the room")
    @Min(value = 0, message = "Square meters cannot be negative ")
    private Double squaredMeters;

    @NotNull(message = "Please provide how many beds the room has")
    @Min(value = 0, message = "Room cannot have negative number of beds")
    private Integer numberOfBeds;

    @NotNull(message = "Please provide how many bathrooms the room has")
    @Min(value = 0, message = "Room cannot have negative number of bathrooms")
    private Integer numberOfBathrooms;

    @NotNull(message = "Please provide how many beds the room has")
    @Min(value = 0, message = "Room cannot have negative number of beds")
    private Integer numberOfBedrooms;

    @Value("false")
    private Boolean hasWifi;

    @Value("false")
    private Boolean hasPool;

    @Value("false")
    private Boolean hasTV;

    @Value("false")
    private Boolean canSmoke;

    @Value("false")
    private Boolean hasDiningRoom;

    @Value("false")
    private Boolean hasBalcony;

    @Value("false")
    private Boolean hasAircondition;

    private Set<@Size(max = 3145728, message = "Only images up to 64KB encoded in base64 are being accepted (87364 characters)")
            String> photos;


}
