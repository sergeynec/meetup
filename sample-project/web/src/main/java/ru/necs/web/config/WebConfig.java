package ru.necs.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ru.necs.domain.spring.DomainConfig;
import ru.necs.web.controller.spi.SPIConfig;

@EnableWebMvc
@Configuration
@Import({DomainConfig.class,SPIConfig.class})
@ComponentScan({"ru.necs.web.controller", "ru.necs.web.orika"})
public class WebConfig {

}
