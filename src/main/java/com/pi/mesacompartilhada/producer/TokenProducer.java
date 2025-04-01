package com.pi.mesacompartilhada.producer;

import com.pi.mesacompartilhada.models.PasswordToken;
import com.pi.mesacompartilhada.records.EmailDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TokenProducer {
    final RabbitTemplate rabbitTemplate;
    public TokenProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @Value("${broker.queue.email.name}")
    private String routingKey;

    public void publishMessage(PasswordToken passwordToken) {
        System.out.println(">>> Enviando email para " + passwordToken.getUserEmail());
        var emailDto = new EmailDto(
                passwordToken.getUserEmail(),
                "Recuperação de senha",
                "Aqui está seu código para recuperação de senha da sua conta na Mesa Compartilhada \n\n " + passwordToken.getToken());
        rabbitTemplate.convertAndSend("", routingKey, emailDto);
    }
}
