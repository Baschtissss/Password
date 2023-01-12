package org.acme.password.user;


import lombok.NonNull;
import org.acme.Hash.HashMethods;
import org.acme.unicornrides.passenger.Passenger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserRepository {

    @Inject
    EntityManager entityManager;

    @Transactional
    public User registerUser(@NonNull final User user) {
        //if(entityManager.find(User.class, user.getEmail()) != null)
        entityManager.persist(user);
        return user;
    }

    public static boolean checkPassword(@NonNull final User user, String input){
        if(HashMethods.hashPassword(input, user.getSalt()) == user.getPassword())
            return true;
        else
            return false;
    }

}
