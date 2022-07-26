package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.consumers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;

import br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities.Employee;
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
	
	@Autowired
	public EmployeeConsumer(Cache<String, Boolean> cache) {
		this.cache = cache;
	}
	
	// @RetryableTopic() non blocking retry
	@KafkaListener(
			id = ConsumerIds.EMPLOYEE_CONSUMER,
			topics = KafkaTopics.EMPLOYEE, 
			groupId = ConsumerGroups.DASHBOARD, // will override properties if set
			containerFactory = BeanNames.EMPLOYEE_CONTAINER_FACTORY)
	public void listen(String message) throws JsonMappingException, JsonProcessingException {
		
		Employee employee = objectMapper.readValue(message, Employee.class);
		
		if (existsInCache(employee.getEmail())) {
			throw new IllegalArgumentException("Email already exists in cache.");
		}
		
		logger.info("Processing: {}", employee);
		
		cache.put(employee.getEmail(), true);
		
	}
	
	private boolean existsInCache(String email) {
		return Optional
					.ofNullable(cache.getIfPresent(email))
					.orElse(false);
	}
	
}
