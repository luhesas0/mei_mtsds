package com.estg.messaging;

import com.estg.dtos.OrdemTrabalhoDTO;
import com.estg.models.OrdemTrabalho;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OTPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public OTPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOT(OrdemTrabalhoDTO ot) {
        rabbitTemplate.convertAndSend("OT_exchange", "OT_routingKey", ot);
        System.out.println("Ordem de trabalho publicada" + ot);
    }


}
