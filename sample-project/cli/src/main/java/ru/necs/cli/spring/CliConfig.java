package ru.necs.cli.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.necs.domain.spring.DomainConfig;

@Configuration
@Import(DomainConfig.class)
@ComponentScan({"ru.necs.cli.service", "ru.necs.cli.orika"})
public class CliConfig {

}
