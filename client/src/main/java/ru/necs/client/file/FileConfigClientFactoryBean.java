package ru.necs.client.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.Resource;
import ru.necs.client.ConfigClient;
import ru.necs.client.common.Converter;

public class FileConfigClientFactoryBean extends AbstractFactoryBean<ConfigClient> {

    private final Map<Class<?>, Converter<String, ?>> convertersRegistry;
    private final Resource file;

    public FileConfigClientFactoryBean(final Map<Class<?>, Converter<String, ?>> convertersRegistry, final Resource file) {
        this.convertersRegistry = convertersRegistry;
        this.file = file;
    }

    @Override
    public Class<?> getObjectType() {
        return ConfigClient.class;
    }

    @Override
    protected ConfigClient createInstance() throws Exception {
        return new FileConfigClient(
                convertersRegistry,
                new FileReader(
                        new ObjectMapper()
                ).read(file)
        );
    }
}
