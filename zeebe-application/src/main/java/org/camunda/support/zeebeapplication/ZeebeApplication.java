package org.camunda.support.zeebeapplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.impl.ZeebeClientBuilderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZeebeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZeebeApplication.class, args);
	}

	@Bean(destroyMethod = "close")
	public ZeebeClient zeebeClient(){
		ZeebeClient client = new ZeebeClientBuilderImpl()
                    .gatewayAddress("zeebe.c8.dev.local:443")
                    .build();

    System.out.println(client.newTopologyRequest().send().join().toString());
	return client;
	}

}
