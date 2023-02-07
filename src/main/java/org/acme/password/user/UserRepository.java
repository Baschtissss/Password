package org.acme.password.user;


import lombok.NonNull;
import org.acme.Hash.HashMethods;
import org.acme.unicornrides.passenger.Passenger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Objects;

@ApplicationScoped
public class UserRepository {

    @Inject
    EntityManager entityManager;

    @Transactional
    public User registerUser(@NonNull @Valid final User user) {
        if(entityManager.find(User.class, user.getEmail()) != null)
            return null;
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public void changePassword(){

    }

    public boolean checkPassword(final User user, String input){
        if(user == null)
            return false;
        if(Objects.equals(HashMethods.hashPassword(input, user.getSalt()), user.getPassword()))
            return true;
        else
            return false;
    }

}
