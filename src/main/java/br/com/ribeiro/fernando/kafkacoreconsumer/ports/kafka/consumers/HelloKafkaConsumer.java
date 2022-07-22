package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.topics.KafkaTopics;

@Component
public class HelloKafkaConsumer {

													// set number of consumers for this topic	
	@KafkaListener(topics = KafkaTopics.HELLO, concurrency = "2")
	public void consume(String message) {
		System.out.println(message);
	}
	
}
