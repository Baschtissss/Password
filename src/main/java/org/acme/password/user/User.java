package org.acme.password.user;


import lombok.*;
import org.acme.Hash.HashMethods;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class User {
    @Id
    private String email;

    private String password;

    private String telephoneNumber;

    private String salt;

    public User(String email, String pw, String phone){
        setPassword(pw);
        setEmail(email);
        setTelephoneNumber(phone);
        setSalt(HashMethods.saltGenerator());
    }
}
