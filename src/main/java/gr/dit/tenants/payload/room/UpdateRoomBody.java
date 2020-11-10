package gr.dit.tenants.payload.room;

import gr.dit.tenants.entities.validation.DatePeriod;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@DatePeriod(startingDateField = "beginDate", endingDateField = "endDate")
public class UpdateRoomBody {

    @NotNull(message = "Please provide a room id")
    private Long id;

    @Size(max = 50, message = "Room name cannot exceed 50 characters")
    private String name;

    @Size(max = 50, message = "Country's name cannot exceed 50 characters")
    private String country;

    @Size(max = 50, message = "City's name cannot exceed 50 characters")
    private String city;

    @Size(max = 50, message = "Address' name cannot exceed 50 characters")
    private String address;

    @FutureOrPresent(message = "Begin date must be present or future")
    private Date beginDate;

    @FutureOrPresent(message = "End date must be present or future")
    private Date endDate;

    @Min(value = 1, message = "At least 1 person must be able to stay")
    private Integer peopleNumber;

    @DecimalMin(value = "0", message = "Price cannot be negative")
    private BigDecimal price;

    @DecimalMin(value = "0", message = "Extra cost per person cannot be negative")
    private BigDecimal extraCostPerPerson;

    @Size(max = 50, message = "Room type description cannot exceed 50 characters")
    private String roomType;

    @Size(max = 255, message = "Rules description cannot exceed 255 characters")
    private String rules;


    @Size(max = 255, message = "Room description cannot exceed 255 characters")
    private String description;

    @Min(value = 0, message = "Square meters cannot be negative")
    private Double squaredMeters;

    @Min(value = 0, message = "Room cannot have negative number of beds")
    private Integer numberOfBeds;

    @Min(value = 0, message = "Room cannot have negative number of bathrooms")
    private Integer numberOfBathrooms;

    @Min(value = 0, message = "Room cannot have negative number of beds")
    private Integer numberOfBedrooms;

    private Boolean hasWifi;
    private Boolean hasPool;
    private Boolean hasTV;
    private Boolean canSmoke;
    private Boolean hasDiningRoom;
    private Boolean hasBalcony;
    private Boolean hasAircondition;

    private Set<@Size(max = 3145728, message = "Only images up to 64KB encoded in base64 are being accepted (87364 characters)")
            String> photos;

}
