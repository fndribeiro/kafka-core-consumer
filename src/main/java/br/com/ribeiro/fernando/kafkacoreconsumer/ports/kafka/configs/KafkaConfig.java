package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.configs;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.filters.EmployeeTypeMessageFilter;
import br.com.ribeiro.fernando.kafkacoreconsumer.ports.spring.configs.BeanNames;

@Configuration
public class KafkaConfig {
	
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

	@Bean(name = BeanNames.EMPLOYEE_TYPE_CONTAINER_FACTORY)
	public ConcurrentKafkaListenerContainerFactory<Object, Object> employeeTypeContainerFactory(ConcurrentKafkaListenerContainerFactoryConfigurer configurer) {
		
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		factory.setRecordFilterStrategy(new EmployeeTypeMessageFilter());
		
		configurer.configure(factory, consumerFactory());
		
		return factory;
	}
	
}
