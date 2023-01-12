package org.acme.unicornrides.passenger;

import lombok.NonNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;

@ApplicationScoped
public class PassengerRepository {
    @Inject
    EntityManager entityManager;

    @Transactional
    public Passenger registerPassenger(@NonNull final Passenger passenger) {
        entityManager.persist(passenger);
        return passenger;
    }

    public Passenger findById(final long id) {
        return entityManager.find(Passenger.class, id);
    }

    public Collection<Passenger> findAll() {
        return entityManager.createNamedQuery("findAllPassengers", Passenger.class).getResultList();
    }

    public Collection<Passenger> findByLastName(@NonNull final String lastName) {
        TypedQuery<Passenger> query = entityManager.createNamedQuery("findPassengersByName", Passenger.class);
        query.setParameter("name", lastName);
        return query.getResultList(); //ersetzen Sie dies durch Verwendung der NamedQuery "findPassengersByName"
    }

}
