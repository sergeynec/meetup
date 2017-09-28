package ru.necs.client.rest;

import static java.util.Optional.ofNullable;
import java.util.Map;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.necs.client.common.AbstractConfigClient;
import ru.necs.client.common.Converter;
import ru.necs.client.model.Key;
import ru.necs.client.rest.model.RestConfigContext;

public class RestConfigClient extends AbstractConfigClient {

    private final RestConfigContext context;

    private final RestTemplate restTemplate;

    public RestConfigClient(final Map<Class<?>, Converter<String, ?>> convertersRegistry, final RestConfigContext context) {
        super(convertersRegistry);

        this.context = context;
        this.restTemplate = new RestTemplate();
    }

    @Override
    protected Optional<String> getStringValue(final Key key) {
        final ResponseEntity<String> entity = restTemplate.postForEntity(
                context.getUrl(),
                key,
                String.class
        );
        if (!entity.getStatusCode().is2xxSuccessful()) {
            throw new HttpClientErrorException(entity.getStatusCode());
        }

        return ofNullable(entity.getBody());
    }
}
