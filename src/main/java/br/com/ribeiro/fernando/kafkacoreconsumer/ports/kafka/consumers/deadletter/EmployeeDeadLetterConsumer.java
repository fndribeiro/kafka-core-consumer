package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.consumers.deadletter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities.Employee;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.topics.KafkaTopics;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.spring.configs.BeanNames;

@Component
public class EmployeeDeadLetterConsumer {

	private final Logger logger = LoggerFactory.getLogger(EmployeeDeadLetterConsumer.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@KafkaListener(topics = KafkaTopics.EMPLOYEE_DEAD_LETTER, errorHandler = BeanNames.EMPLOYEE_ERROR_HANDLER) // set local error handler. will override global error handler.
	public void listenDeadLetter(String message) throws JsonMappingException, JsonProcessingException {
		
		Employee employee = objectMapper.readValue(message, Employee.class);
		
		logger.info("Processing dead message: {}", employee);
		
	}
	
}
