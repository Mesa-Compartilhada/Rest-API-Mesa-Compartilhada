package com.pi.mesacompartilhada.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;

@Document(collection="token")
@Getter
@Setter
public class PasswordToken {

    @Id
    private String id;

    @Field(targetType = FieldType.BINARY)
    private String token;
    private boolean status;
    private String userEmail;
    private LocalDateTime exp;

    public PasswordToken(String token, boolean status, String userEmail, LocalDateTime exp) {
        this.token = token;
        this.status = status;
        this.userEmail = userEmail;
        this.exp = exp;
    }

}
