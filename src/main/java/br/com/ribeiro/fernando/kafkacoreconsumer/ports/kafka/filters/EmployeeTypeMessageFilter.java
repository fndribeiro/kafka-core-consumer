package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.filters;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities.Employee;
import br.com.ribeiro.fernando.kafkacoreconsumer.domain.valueobjects.EmployeeType;

public class EmployeeTypeMessageFilter implements RecordFilterStrategy<Object, Object> {
	
	private final Logger logger = LoggerFactory.getLogger(EmployeeTypeMessageFilter.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
		
		try {
			
			Employee employee = objectMapper.readValue(consumerRecord.value().toString(), Employee.class);
			
			boolean filterOut = EmployeeType.REGULAR.equals(employee.getType());
			
			logger.info("Filtering employee type {}. Filter out: {}",
					employee.getType(),
					filterOut);
			
			return filterOut;
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} 
		
		return false;
	}
	
}
