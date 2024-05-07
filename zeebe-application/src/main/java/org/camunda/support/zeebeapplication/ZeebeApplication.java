package org.camunda.support.zeebeapplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.ZeebeClientBuilderImpl;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Deployment(resources = "classpath*:*.bpmn")
public class ZeebeApplication {
	private static Logger log = LoggerFactory.getLogger(ZeebeApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ZeebeApplication.class, args);
	}

//	@Bean(destroyMethod = "close")
//	public ZeebeClient zeebeClient() {
//
//		ZeebeClient client = new ZeebeClientBuilderImpl()
//				.gatewayAddress("zeebe.c8.dev.local:443")
//				.build();
//		log.info("connecting to Zeebe");
//		log.info(client.newTopologyRequest().send().join().toString());
//
//		return client;
//	}
}