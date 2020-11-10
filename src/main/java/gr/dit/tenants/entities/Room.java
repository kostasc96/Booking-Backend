package gr.dit.tenants.entities;

import com.fasterxml.jackson.annotation.*;
import gr.dit.tenants.entities.validation.DatePeriod;
import gr.dit.tenants.view.Views;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.*;


@Entity
@Table(name="room")
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatePeriod(startingDateField = "beginDate",endingDateField = "endDate")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="roomId", scope = Room.class)
public class Room {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Public.class)
	private Long roomId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JsonView(Views.Public.class)
	private User owner;

	@NotBlank
	@Size(max = 50)
	@Column(nullable = false,length = 50)
	@JsonView(Views.Public.class)
	private String name;

	@NotBlank
	@Size(max = 50)
	@Column(nullable = false,length = 50)
	@JsonView(Views.Public.class)
	private String country;

	@NotBlank
	@Size(max = 50)
	@Column(nullable = false,length = 50)
	@JsonView(Views.Public.class)
	private String city;

	@NotBlank
	@Size(max = 50)
	@JsonView(Views.Public.class)
	private String address;

	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(Views.Public.class)
	private Date beginDate;

	@NotNull
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(Views.Public.class)
	private Date endDate;

	@NotNull
	@Min(value = 1)
	@JsonView(Views.Public.class)
	@Column(nullable = false)
	private Integer peopleNumber;

	@NotNull
	@DecimalMin(value = "0", inclusive = false)
	@Column(nullable = false, precision = 9 , scale = 2)
	@JsonView(Views.Public.class)
	private BigDecimal price;

	@NotNull
	@DecimalMin(value = "0")
	@Column(nullable = false, precision = 9, scale = 2)
	@JsonView(Views.Public.class)
	private BigDecimal extraCostPerPerson;

	@NotNull
	@Size(max = 50)
	@Column(nullable = false, length = 50)
	@JsonView(Views.Public.class)
	private String roomType;

	@Size(max = 255)
	@Column(nullable = false, length = 255)
	@JsonView(Views.Public.class)
	private String rules;

	@Size(max = 255)
	@Column(nullable = false, length = 255)
	@JsonView(Views.Public.class)
	private String description;

	@NotNull
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Double squaredMeters;

	@NotNull
	@Min(value = 0)
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Integer numberOfBeds;

	@NotNull
	@Min(value = 0)
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Integer numberOfBedrooms;

	@NotNull
	@Min(value = 0)
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Integer numberOfBathrooms;

	@NotNull
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Boolean hasWifi;

	@NotNull
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Boolean hasPool;

	@NotNull
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Boolean hasTV;

	@NotNull
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Boolean canSmoke;

	@NotNull
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Boolean hasDiningRoom;

	@NotNull
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Boolean hasBalcony;

	@NotNull
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Boolean hasAircondition;

	@ElementCollection(fetch = FetchType.EAGER)
	@Column(length = 3145728) //MAX 64KB image - To store n bytes in base64: 4*(n/3)
	@JsonView(Views.Public.class)
	private Set<String> photos;

	@OneToMany(mappedBy = "room",cascade = CascadeType.REMOVE)
	@JsonView(Views.Public.class)
	private Set<Rating> ratings;

	@Transient
	@JsonView(Views.Public.class)
	private Double ratingAvg;

	@PostLoad
	@JsonView(Views.Public.class)
	private void calculateAverageRating()
	{
		this.ratingAvg = this.getRatings()
				.stream()
				.mapToDouble(x-> x.getRating())
				.average()
				.orElse(0);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Room room = (Room) o;
		return Objects.equals(roomId, room.roomId);
	}

	@Override
	public int hashCode() {
		return 69;
	}
}
