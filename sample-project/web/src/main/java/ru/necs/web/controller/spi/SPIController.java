package ru.necs.web.controller.spi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.necs.domain.service.ConfigService;

@Component
public abstract class SPIController {

	@Autowired
	protected ConfigService service;
}
