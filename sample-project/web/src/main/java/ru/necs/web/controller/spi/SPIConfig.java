package ru.necs.web.controller.spi;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.serviceloader.ServiceListFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SPIConfig {

	@Bean
	public ServiceListFactoryBean serviceBeans() {
		ServiceListFactoryBean serviceListFactoryBean = new ServiceListFactoryBean();
		serviceListFactoryBean.setServiceType(SPIController.class);
		return serviceListFactoryBean;
	}
	
	@Bean 
	public SPIController getSpiController(ServiceListFactoryBean serviceListFactoryBean) throws Exception {
		List<?> spiServices  = (List<?>) serviceListFactoryBean.getObject();
		if (!Validate.notNull(spiServices).isEmpty() && spiServices.get(0) instanceof SPIController) {
			return (SPIController) spiServices.get(0);	
		} else {
			throw new NoSuchBeanDefinitionException(SPIController.class);
		}
		
	}
}
