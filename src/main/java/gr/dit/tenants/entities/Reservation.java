package gr.dit.tenants.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import gr.dit.tenants.entities.validation.DatePeriod;
import gr.dit.tenants.view.Views;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="reservation")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatePeriod(startingDateField = "fromDate", endingDateField = "toDate")
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="reservationId", scope = Reservation.class)
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.CurrentUser.class)
	private Long reservationId;

	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonView(Views.CurrentUser.class)
	@JsonIgnore
	private User renter;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView(Views.Public.class)
	private Room room;

	@NotNull
	//@FutureOrPresent
	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(Views.Public.class)
	private Date fromDate;

	@NotNull
	//@FutureOrPresent
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@JsonView(Views.Public.class)
	private Date toDate;

	@NotNull
	@Min(value = 1)
	@JsonView(Views.CurrentUser.class)
	private Integer persons;

	@NotNull
	@DecimalMin(value = "0")
	@Column(nullable = false, precision = 9 , scale = 2)
	@JsonView(Views.CurrentUser.class)
	private BigDecimal price;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonView(Views.Public.class)
	private Date creationTime;

}
