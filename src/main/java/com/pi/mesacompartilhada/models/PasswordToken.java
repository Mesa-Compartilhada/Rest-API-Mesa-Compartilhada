package com.pi.mesacompartilhada.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection="token")
@Getter
@Setter
public class PasswordToken {

    @Id
    private String id;

    private UUID token;
    private boolean status;
    private String userEmail;
    private LocalDateTime exp;

    public PasswordToken(UUID token, boolean status, String userEmail, LocalDateTime exp) {
        this.token = token;
        this.status = status;
        this.userEmail = userEmail;
        this.exp = exp;
    }

}
