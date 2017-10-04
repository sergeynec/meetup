package ru.necs.web.controller.spi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.serviceloader.ServiceListFactoryBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextClosedEvent;

import ru.necs.domain.service.ConfigService;

@Configuration
public class SPIConfig {

	@Autowired
	private ConfigurableApplicationContext context;
	
	
	@Bean
	public ServiceListFactoryBean serviceBeans() {
		ServiceListFactoryBean serviceListFactoryBean = new ServiceListFactoryBean();
		serviceListFactoryBean.setServiceType(SPIController.class);
		return serviceListFactoryBean;
	}
	
	@SuppressWarnings("unchecked")
	@Bean 
	public List<SPIController> getSpiController(ServiceListFactoryBean serviceListFactoryBean, ConfigService service) throws Exception {
		List<SPIController> spiServices  = (List<SPIController>) serviceListFactoryBean.getObject();
		spiServices.forEach(spi->spi.init(service));
		context.addApplicationListener((ContextClosedEvent event)->spiServices.forEach(spi->spi.destroy()));
		return spiServices;	
	}
}
