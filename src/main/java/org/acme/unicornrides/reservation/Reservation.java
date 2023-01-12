package org.acme.unicornrides.reservation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "reservation_date")
    private LocalDate date;

}
