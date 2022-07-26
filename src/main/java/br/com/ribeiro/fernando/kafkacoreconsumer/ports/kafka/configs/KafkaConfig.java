package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.configs;

import java.time.Duration;
import java.util.Map;

import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.filters.EmployeeTypeMessageFilter;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.topics.KafkaTopics;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.spring.configs.BeanNames;

@Configuration
public class KafkaConfig {
	
	private final long retryInterval = Duration.ofSeconds(2).getSeconds();
	private final long retryMaxAttempts = 1;
	
	private KafkaProperties kafkaProperties;
	
	@Autowired
	public KafkaConfig(KafkaProperties kafkaProperties) {
		this.kafkaProperties = kafkaProperties;
	}

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory() {
		
		Map<String,Object> properties = kafkaProperties.buildConsumerProperties();
		
		return new DefaultKafkaConsumerFactory<>(properties);
	}

	@Bean(name = BeanNames.EMPLOYEE_CONTAINER_FACTORY)
	public ConcurrentKafkaListenerContainerFactory<Object, Object> employeeContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer, KafkaTemplate<String, String> kafkaTemplate) {
		
		var deadLetterRecoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
				(record, exception) -> new TopicPartition(KafkaTopics.EMPLOYEE_DEAD_LETTER, record.partition()));
		
		var errorHandler = new DefaultErrorHandler(deadLetterRecoverer, new FixedBackOff(retryInterval, retryMaxAttempts));
		
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		factory.setRecordFilterStrategy(new EmployeeTypeMessageFilter());
		factory.setCommonErrorHandler(errorHandler); // set global handler
		
		configurer.configure(factory, consumerFactory());
		
		return factory;
	}
	
}
