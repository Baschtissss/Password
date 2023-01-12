package org.acme.unicornrides.unicorn;

import lombok.NonNull;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@ApplicationScoped
public class UnicornRepository {
    @Inject
    EntityManager entityManager;

    public Unicorn findById(long id) {
        return entityManager.find(Unicorn.class, id);
    }

    public Collection<Unicorn> findAll() {
        return entityManager.createNamedQuery("findAllUnicorns", Unicorn.class).getResultList();
    }

    public Optional<Unicorn> findUnicornWithLeastReservations() {
        return entityManager.createNamedQuery("findUnicornWithLeastReservations", Unicorn.class)
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Transactional
    public Unicorn create(@NonNull final Unicorn unicorn) {
        entityManager.persist(unicorn);
        return unicorn;
    }

    @Transactional
    public Unicorn update(@NonNull final Unicorn unicorn) {
        final Unicorn updatedUnicorn = entityManager.merge(unicorn);
        return updatedUnicorn;
    }
}