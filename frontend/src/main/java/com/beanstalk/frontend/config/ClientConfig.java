package com.beanstalk.frontend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.beanstalk.frontend.client.BeanStalkClient;

@Configuration
public class ClientConfig {
    @Bean
	BeanStalkClient beanStalkClient() {
		WebClient client = WebClient.builder()
			.baseUrl("http://localhost:8080/api")
			.build();
		
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
			.builderFor(WebClientAdapter.create(client))
			.build();
		
		return factory.createClient(BeanStalkClient.class);
	}
}
