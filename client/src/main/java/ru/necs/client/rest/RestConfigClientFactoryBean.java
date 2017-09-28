package ru.necs.client.rest;

import java.util.Map;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import ru.necs.client.ConfigClient;
import ru.necs.client.common.Converter;
import ru.necs.client.rest.model.RestConfigContext;

public class RestConfigClientFactoryBean extends AbstractFactoryBean<ConfigClient> {

    private final Map<Class<?>, Converter<String, ?>> convertersRegistry;
    private final RestConfigContext context;

    public RestConfigClientFactoryBean(final Map<Class<?>, Converter<String, ?>> convertersRegistry, final RestConfigContext context) {
        this.convertersRegistry = convertersRegistry;
        this.context = context;
    }

    @Override
    public Class<?> getObjectType() {
        return ConfigClient.class;
    }

    @Override
    protected ConfigClient createInstance() throws Exception {
        return new RestConfigClient(convertersRegistry, context);
    }
}
