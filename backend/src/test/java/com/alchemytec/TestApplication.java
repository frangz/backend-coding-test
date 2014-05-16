package com.alchemytec;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(excludeFilters={@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=com.alchemytec.Application.class)})
@Configuration
@EnableAutoConfiguration
@EnableJpaRepositories
public class TestApplication {

}
