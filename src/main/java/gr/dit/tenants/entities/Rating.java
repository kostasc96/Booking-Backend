package gr.dit.tenants.entities;

import com.fasterxml.jackson.annotation.*;
import gr.dit.tenants.view.Views;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name="rating")
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="ratingId", scope = Rating.class)
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonView(Views.Public.class)
	private Long ratingId;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView(Views.Public.class)
	private User creator;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView(Views.Public.class)
	private Room room;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonView(Views.Public.class)
	private Reservation reservation;

	@NotNull
	@Min(value = 1)
	@Max(value = 5)
	@Column(nullable = false)
	@JsonView(Views.Public.class)
	private Integer rating;

	@Size(max = 255)
	@Column(nullable = false,length = 255)
	@JsonView(Views.Public.class)
	private String description;

	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonView(Views.Public.class)
	private Date creationTime;

}