package ru.necs.web.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import ma.glasnost.orika.BoundMapperFacade;
import ma.glasnost.orika.MapperFactory;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.necs.common.test.EntityGenerator;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.random.MeetupRandom;
import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;
import ru.necs.domain.service.ConfigService;
import ru.necs.web.model.AttributeJs;
import ru.necs.web.model.KeyJs;
import ru.necs.web.model.ValueJs;

@Test
public class HelloControllerTest {

    private static final MeetupRandom RANDOM = new MeetupRandom();

    @Mock
    private ConfigService service;

    @Mock
    private MapperFactory mapperFactory;

    @Mock
    private BoundMapperFacade<KeyJs, Key> requestMapper;

    @Mock
    private BoundMapperFacade<Value, ValueJs> responseMapper;

    @BeforeMethod
    public void before() throws Exception {
        initMocks(this);

        when(mapperFactory.getMapperFacade(KeyJs.class, Key.class)).thenReturn(requestMapper);
        when(mapperFactory.getMapperFacade(Value.class, ValueJs.class)).thenReturn(responseMapper);
    }

    @Test
    @EntityGenerators(KeyJsGenerator.class)
    public void callDomainServiceToLookupValue(final KeyJs keyJs) throws Exception {
        final Key key = mock(Key.class);
        final Value value = mock(Value.class);
        final ValueJs valueJs = mock(ValueJs.class);

        when(requestMapper.map(keyJs)).thenReturn(key);
        when(service.lookup(key)).thenReturn(value);
        when(responseMapper.map(value)).thenReturn(valueJs);

        final ValueJs actual = new HelloController(service, mapperFactory).say(keyJs);

        assertThat(actual).isSameAs(valueJs);

        final InOrder inOrder = inOrder(service, requestMapper, responseMapper);
        inOrder.verify(requestMapper).map(keyJs);
        inOrder.verify(service).lookup(key);
        inOrder.verify(responseMapper).map(value);

        verifyNoMoreInteractions(service, requestMapper, responseMapper);
        verifyZeroInteractions(key, value, valueJs);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void throwIfMapperFactoryReturnNullRequestMapperFacade() throws Exception {
        reset(mapperFactory);
        when(mapperFactory.getMapperFacade(KeyJs.class, Key.class)).thenReturn(null);
        when(mapperFactory.getMapperFacade(Value.class, ValueJs.class)).thenReturn(responseMapper);

        new HelloController(service, mapperFactory);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void throwIfMapperFactoryReturnNullResponseMapperFacade() throws Exception {
        reset(mapperFactory);

        when(mapperFactory.getMapperFacade(KeyJs.class, Key.class)).thenReturn(requestMapper);
        when(mapperFactory.getMapperFacade(Value.class, ValueJs.class)).thenReturn(null);

        new HelloController(service, mapperFactory);
    }

    private static class KeyJsGenerator implements EntityGenerator<KeyJs> {

        @Override
        public KeyJs generate() {
            return new KeyJs()
                    .setName(RANDOM.nextUuidString())
                    .setAttributes(
                            RANDOM.nextList(
                                    () -> new AttributeJs().setKey(RANDOM.nextUuidString()).setValue(RANDOM.nextUuidString())
                            )
                    );
        }
    }
}
