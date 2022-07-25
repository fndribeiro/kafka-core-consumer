package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.errors.handlers;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import br.com.ribeiro.fernando.kafkacoreconsumer.ports.spring.configs.BeanNames;

@Component(value = BeanNames.EMPLOYEE_ERROR_HANDLER)
public class EmployeeErrorHandler implements ConsumerAwareListenerErrorHandler {
	
	private final Logger logger = LoggerFactory.getLogger(EmployeeErrorHandler.class);

	@Override
	public Object handleError(Message<?> message, ListenerExecutionFailedException exception, Consumer<?, ?> consumer) {
		
		logger.warn("EmployeeProducer error. Sending to elasticsearch : {}. Reason: {}",
				message.getPayload(),
				exception.getMessage());
		
		return null;
	}

	
	
}
