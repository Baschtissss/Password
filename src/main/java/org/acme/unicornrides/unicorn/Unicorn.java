package org.acme.unicornrides.unicorn;

import lombok.*;
import org.acme.unicornrides.reservation.Reservation;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "reservations")

@NamedQueries({
        @NamedQuery(name = "findAllUnicorns", query = "select u from Unicorn u order by u.name"),
        @NamedQuery(name = "findUnicornWithLeastReservations", query = "select u from Unicorn u order by size(u.reservations)")
})

@Entity
public class Unicorn {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private int weightKg;
    private int maxSpeedKmpH;

    @OneToMany
    @JoinColumn(name = "unicorn_id")
    private List<Reservation> reservations;

}
