package com.beanstalk.backend.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EntityScan("com.beanstalk.backend.domain")
@EnableJpaRepositories("com.beanstalk.backend.repos")
@EnableTransactionManagement
public class DomainConfig {
}
