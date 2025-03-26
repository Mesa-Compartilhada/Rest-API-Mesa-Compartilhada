package com.pi.mesacompartilhada.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection="token")
@Getter
@Setter
public class PasswordToken {

    @Id
    private String id;

    private String token;
    private boolean status;
    private String userEmail;
    private LocalDate exp;

    public PasswordToken(String token, boolean status, String userEmail, LocalDate exp) {
        this.token = token;
        this.status = status;
        this.userEmail = userEmail;
        this.exp = exp;
    }

}
