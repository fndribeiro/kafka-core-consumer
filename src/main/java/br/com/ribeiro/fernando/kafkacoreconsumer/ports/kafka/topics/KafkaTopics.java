package br.com.ribeiro.fernando.kafkacoreconsumer.ports.kafka.topics;

public class KafkaTopics {
	
	private final static String TOPIC_PREFIX = "nemhdkh3-";
	private final static String DEAD_LETTER = "-dead-letter";
	
	public final static String HELLO = TOPIC_PREFIX + "hello";
	public final static String EMPLOYEE = TOPIC_PREFIX + "employee";
	public final static String EMPLOYEE_DEAD_LETTER = EMPLOYEE + DEAD_LETTER;
	
}
