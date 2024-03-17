package com.springkafkaexample.demo;

import com.springkafkaexample.demo.service.ConvertBookService;
import com.springkafkaexample.demo.service.impl.ConvertBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.utility.DockerImageName;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@TestConfiguration(proxyBeanMethods = false)
@Import({
		ConvertBookServiceImpl.class,
		TestSpringKafkaExampleApplication.ValidationTestConfig.class
})
public class TestSpringKafkaExampleApplication  {

	@Autowired
	public ConvertBookService convertBookService;

	@Bean
	@ServiceConnection
	KafkaContainer kafkaContainer() {
		return new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));
	}

	public static void main(String[] args) {
		SpringApplication.from(SpringKafkaExampleApplication::main).with(TestSpringKafkaExampleApplication.class).run(args);
	}

	@TestConfiguration
	static class ValidationTestConfig {

		@Bean
		ValidatorFactory validatorFactory() {
			return Validation.buildDefaultValidatorFactory();
		}

		@Bean
		Validator validator(ValidatorFactory validatorFactory) {
			return validatorFactory.getValidator();
		}
	}
}
