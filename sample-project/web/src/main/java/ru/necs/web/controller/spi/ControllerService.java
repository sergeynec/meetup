package ru.necs.web.controller.spi;

import java.util.ServiceLoader;

public class ControllerService {

	private ServiceLoader<SPIController> loader;

	public ControllerService(ServiceLoader<SPIController> loader) {
		this.loader = loader;
	}

}
