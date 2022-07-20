package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.consumers;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.topics.KafkaTopics;

@Service
public class HelloKafkaConsumer {

																			// set number of consumers for this topic	
	@KafkaListener(topics = KafkaTopics.TOPIC_PREFIX + KafkaTopics.HELLO, concurrency = "2")
	public void consume(String message) {
		System.out.println(message);
	}
	
}
