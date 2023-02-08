package org.acme.password;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.acme.Hash.HashMethods;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Random;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ResetCode {

    @Id
    private String email;

    private String code;

    private String salt;

    private LocalDateTime time;

    public ResetCode(String email, String salt){
        setEmail(email);
        setSalt(salt);
        setTime(LocalDateTime.now());
    }

    public String generateCode(){
        Random random = new Random();

        StringBuilder temp = new StringBuilder();
        for(int i = 0; i<8;i++){
            temp.append((char) random.nextInt(65, 91));
        }
        setCode(HashMethods.hashPassword(temp.toString(), getSalt()));
        return temp.toString();
    }
}
