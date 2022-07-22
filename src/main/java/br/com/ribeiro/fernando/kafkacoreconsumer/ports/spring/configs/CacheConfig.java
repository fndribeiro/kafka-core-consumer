package br.com.ribeiro.fernando.kafkacoreconsumer.ports.spring.configs;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Configuration
public class CacheConfig {
	
	@Bean(name = BeanNames.EMPLOYEE_CACHE)
	public Cache<String, Boolean> employeeCache() {
		return Caffeine
					.newBuilder()
					.expireAfterWrite(Duration.ofMinutes(2))
					.maximumSize(100)
					.build();
	}
	
}
