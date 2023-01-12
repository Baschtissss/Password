package org.acme.unicornrides.passenger;

import lombok.*;
import org.acme.unicornrides.reservation.Reservation;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@NamedQueries({
        @NamedQuery(name = "findAllPassengers", query = "select p from Passenger p order by p.lastName"),
        @NamedQuery(name = "findPassengersByName", query = "select p from Passenger p where p.lastName = :name order by p.lastName")
})

@Entity
public class Passenger {
    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private LocalDate registrationDate;

    @OneToMany
    @JoinColumn(name = "passenger_id")
    private List<Reservation> reservations;

}
