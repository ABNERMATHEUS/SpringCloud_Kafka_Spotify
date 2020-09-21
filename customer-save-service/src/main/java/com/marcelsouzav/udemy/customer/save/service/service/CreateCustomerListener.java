package com.marcelsouzav.udemy.customer.save.service.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelsouzav.udemy.customer.save.service.domain.Customer;
import com.marcelsouzav.udemy.customer.save.service.gateway.json.CustomerJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateCustomerListener {

    @Autowired
    private CreateCustomerService createCustomerService;  //classe para criar no banco

    @KafkaListener(topics = "${kafka.topic.request-topic}") //está esperando o tópico (requesttopic2)"escutando"
    @SendTo ///dando o retorno no replay
    public String listen(String json) throws InterruptedException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        //convertendo string em obj
        CustomerJson customerJson = mapper.readValue(json, CustomerJson.class);

        UUID uuid = createCustomerService.execute(Customer
                .builder()
                .country(customerJson.getCountry())
                .musicStyle(customerJson.getMusicStyle())
                .name(customerJson.getName())
                .build()
        );

        customerJson.setUuid(uuid.toString());

        //convertendo esse objeto para string e retornando
        return mapper.writeValueAsString(customerJson);
    }

}