package ru.necs.web.controller.spi;

import ru.necs.domain.service.ConfigService;

public abstract class SPIController {

	public abstract void init(ConfigService service);

	public abstract void destroy();
}
