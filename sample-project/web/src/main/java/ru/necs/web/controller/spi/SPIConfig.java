package ru.necs.web.controller.spi;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.serviceloader.ServiceListFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
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
	
	@Bean 
	public List<SPIController> getSpiController(ServiceListFactoryBean serviceListFactoryBean, ConfigService service) throws Exception {
		List<SPIController> spiServices  = (List<SPIController>) serviceListFactoryBean.getObject();
		spiServices.forEach(spi->spi.init(service));
		context.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {

			@Override
			public void onApplicationEvent(ContextClosedEvent event) {
				spiServices.forEach(spi->spi.destroy());
				
			}
		});
		return spiServices;	
		
	}
}
