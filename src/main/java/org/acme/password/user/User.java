package org.acme.password.user;


import lombok.*;
import org.acme.Hash.HashMethods;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.validation.constraints.Email;

import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode

@Entity
public class User {
    @Id
    @Email
    private String email;

    @NotNull
    private String password;

    private String telephoneNumber;

    private String salt;

    public User(String email,String pw, String phone){
        setSalt(HashMethods.saltGenerator());
        setPassword(HashMethods.hashPassword(pw, getSalt()));
        setEmail(email);
        setTelephoneNumber(phone);
    }

    public void hashPw(){
        setSalt(HashMethods.saltGenerator());
        setPassword(HashMethods.hashPassword(getPassword(), getSalt()  ));
    }

    public String generatePassword(){
        Random random = new Random();

        StringBuilder temp = new StringBuilder();
        for(int i = 0; i<8;i++){
            temp.append((char) random.nextInt(65, 91));
        }
        setPassword(HashMethods.hashPassword(temp.toString(), getSalt()));
        return temp.toString();

    }
}
