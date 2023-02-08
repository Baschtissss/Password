package org.acme.password.user;


import com.mysql.cj.x.protobuf.MysqlxSession;
import lombok.NonNull;
import org.acme.Hash.HashMethods;
import org.acme.password.ResetCode;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Objects;

@ApplicationScoped
public class UserRepository {

    @Inject
    EntityManager entityManager;

    @Transactional
    public User registerUser(@NonNull @Valid final User user) {

        if(user.getSalt() == null)
            user.hashPw();

        if(entityManager.find(User.class, user.getEmail()) != null)
            return null;
        entityManager.persist(user);
        return user;
    }

    @Transactional
    public String forgotPassword(final String email){
        User user = getUserByEmail(email);

        if(user == null) {
            return null;
        }

        ResetCode code = new ResetCode(user.getEmail(), user.getSalt());
        String temp = code.generateCode();
        Query query = entityManager.createQuery("Delete ResetCode r where r.email = ?1");
        query.setParameter(1, email);
        query.executeUpdate();
        entityManager.persist(code);

        return temp;
    }

    @Transactional
    public String checkCode(final String email, String code){
        ResetCode resetCode = getCodeByEmail(email);

        if(resetCode == null)
            return null;

        if(resetCode.getTime().isBefore(LocalDateTime.now().minusMinutes(15))){
            return null;
        }

        if(Objects.equals(HashMethods.hashPassword(code, resetCode.getSalt()), resetCode.getCode())) {
            User user = getUserByEmail(email);
            String temp = user.generatePassword();
            updatePassword(user.getEmail(), user.getPassword());
            return temp;
        }

        return null;
    }

    @Transactional
    private void updatePassword(String email, String pw){
        Query query = entityManager.createQuery("UPDATE User s set s.password = ?1 where s.email = ?2");
        query.setParameter(1, pw);
        query.setParameter(2, email);
        query.executeUpdate();
    }

    public boolean checkPassword(final String email, String input){
        if(email == null)
            return false;

        User user = getUserByEmail(email);

        if(user == null)
            return false;

        if(Objects.equals(HashMethods.hashPassword(input, user.getSalt()), user.getPassword()))
            return true;

        return false;
    }

    @Transactional
    public boolean changePassword(final String email, final String oldPw, final String newPw){

        User user = getUserByEmail(email);

        if(user == null)
            return false;

        if(!checkPassword(email, oldPw))
            return false;

        updatePassword(email, HashMethods.hashPassword(newPw,user.getSalt()));

        return true;
    }

    public User getUserByEmail(final String email){
        return entityManager.find(User.class,email);
    }

    public ResetCode getCodeByEmail(final String email){
        return entityManager.find(ResetCode.class,email);
    }

}
