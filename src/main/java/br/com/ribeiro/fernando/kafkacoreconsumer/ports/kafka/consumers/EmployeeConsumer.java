package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.consumers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;

import br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities.Employee;
import br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities.EmployeeReplyMessage;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.databases.repositories.EmployeeRepository;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.consumers.groups.ConsumerGroups;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.topics.KafkaTopics;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.spring.configs.BeanNames;

@Component
public class EmployeeConsumer {

	private final Logger logger = LoggerFactory.getLogger(EmployeeConsumer.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	// If no unique value is present, create class with a combination of values and use as key.
	@Qualifier(BeanNames.EMPLOYEE_CACHE)
	private Cache<String, Boolean> cache;
	
	private EmployeeRepository repository;
	
	@Autowired
	public EmployeeConsumer(Cache<String, Boolean> cache, EmployeeRepository repository) {
		this.cache = cache;
		this.repository = repository;
	}
	
	// @RetryableTopic() non blocking retry
	@SendTo(KafkaTopics.EMPLOYEE_REPLY)
	@KafkaListener(
			id = ConsumerIds.EMPLOYEE_CONSUMER,
			topics = KafkaTopics.EMPLOYEE, 
			groupId = ConsumerGroups.DASHBOARD, // will override properties if set
			containerFactory = BeanNames.EMPLOYEE_CONTAINER_FACTORY)
	public String listen(String message) throws JsonProcessingException {
		
		try {
			
			Employee employee = objectMapper.readValue(message, Employee.class);
			
			if (existsInCache(employee.getEmail())) {
				throw new IllegalArgumentException("Email already exists in cache.");
			}
			
			logger.info("Processing: {}", employee);
			
			cache.put(employee.getEmail(), true);
			
			repository.save(employee);
			
			var replyMessage = new EmployeeReplyMessage(true, "Employee saved successfully.");
			
			return objectMapper.writeValueAsString(replyMessage);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			var replyMessage = new EmployeeReplyMessage(false, e.getMessage());
			
			return objectMapper.writeValueAsString(replyMessage);
		}
		
	}
	
	private boolean existsInCache(String email) {
		return Optional
					.ofNullable(cache.getIfPresent(email))
					.orElse(false);
	}
	
}
