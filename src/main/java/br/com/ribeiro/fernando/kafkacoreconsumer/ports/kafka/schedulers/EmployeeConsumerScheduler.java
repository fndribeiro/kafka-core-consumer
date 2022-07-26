package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.consumers.ConsumerIds;

@Component
public class EmployeeConsumerScheduler {

	private final Logger logger = LoggerFactory.getLogger(EmployeeConsumerScheduler.class);
	
	private final String dailyAtEleven = "0 0 23 1/1 * ?";
	private final String dailyAtMidnight = "0 0 0 1/1 * ?";
	
	private KafkaListenerEndpointRegistry registry;
	
	@Autowired
	public EmployeeConsumerScheduler(KafkaListenerEndpointRegistry registry) {
		this.registry = registry;
	}
	
	@Scheduled(cron = dailyAtEleven) 
	public void stop() {
		
		logger.info("Stoping consumer {}", ConsumerIds.EMPLOYEE_CONSUMER);
		
		registry
			.getListenerContainer(ConsumerIds.EMPLOYEE_CONSUMER)
			.stop();
		
	}
	
	@Scheduled(cron = dailyAtMidnight) 
	public void resume() {
		
		logger.info("Resuming consumer {}", ConsumerIds.EMPLOYEE_CONSUMER);
		
		registry
			.getListenerContainer(ConsumerIds.EMPLOYEE_CONSUMER)
			.resume();
		
	}
	
}
