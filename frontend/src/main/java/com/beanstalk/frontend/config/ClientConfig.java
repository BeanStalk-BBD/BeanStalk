package com.beanstalk.frontend.config;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import com.beanstalk.frontend.client.BeanStalkClient;
import com.beanstalk.frontend.shell.ShellHelper;

@Configuration
public class ClientConfig {
    @Bean
	BeanStalkClient beanStalkClient() {
		WebClient client = WebClient.builder()
			.baseUrl("http://beanstalk.eu-west-1.elasticbeanstalk.com/api")
			.build();
		
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
			.builderFor(WebClientAdapter.create(client))
			.build();
		
		return factory.createClient(BeanStalkClient.class);
	}

	@Bean
    public ShellHelper shellHelper(@Lazy Terminal terminal, @Lazy LineReader lineReader) {
            return new ShellHelper(terminal, lineReader);
    }

}
