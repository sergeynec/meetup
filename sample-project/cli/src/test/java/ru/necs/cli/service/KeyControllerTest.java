package ru.necs.cli.service;

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
import ru.necs.cli.model.AttributeCli;
import ru.necs.cli.model.KeyCli;
import ru.necs.cli.model.ValueCli;
import ru.necs.common.test.EntityGenerator;
import ru.necs.common.test.EntityGenerators;
import ru.necs.common.test.random.MeetupRandom;
import ru.necs.domain.model.Key;
import ru.necs.domain.model.Value;
import ru.necs.domain.service.ConfigService;

@Test
public class KeyControllerTest {

    private static final MeetupRandom RANDOM = new MeetupRandom();

    @Mock
    private ConfigService configService;

    @Mock
    private MapperFactory mapperFactory;

    @Mock
    private BoundMapperFacade<KeyCli, Key> requestMapper;

    @Mock
    private BoundMapperFacade<Value, ValueCli> responseMapper;

    @BeforeMethod
    public void before() throws Exception {
        initMocks(this);

        when(mapperFactory.getMapperFacade(KeyCli.class, Key.class)).thenReturn(requestMapper);
        when(mapperFactory.getMapperFacade(Value.class, ValueCli.class)).thenReturn(responseMapper);
    }

    @Test
    @EntityGenerators(KeyCliGenerator.class)
    public void test(final KeyCli request) throws Exception {
        final Key key = mock(Key.class);
        final Value value = mock(Value.class);
        final ValueCli valueCli = mock(ValueCli.class);

        when(requestMapper.map(request)).thenReturn(key);
        when(configService.lookup(key)).thenReturn(value);
        when(responseMapper.map(value)).thenReturn(valueCli);

        final ValueCli actual = new KeyController(configService, mapperFactory).lookup(request);

        assertThat(actual).isSameAs(valueCli);

        final InOrder inOrder = inOrder(requestMapper, configService, responseMapper);
        inOrder.verify(requestMapper).map(request);
        inOrder.verify(configService).lookup(key);
        inOrder.verify(responseMapper).map(value);

        verifyNoMoreInteractions(requestMapper, configService, responseMapper);
        verifyZeroInteractions(key, value, valueCli);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void throwIfMapperFactoryReturnNullRequestMapperFacade() throws Exception {
        reset(mapperFactory);

        when(mapperFactory.getMapperFacade(KeyCli.class, Key.class)).thenReturn(null);
        when(mapperFactory.getMapperFacade(Value.class, ValueCli.class)).thenReturn(responseMapper);

        new KeyController(configService, mapperFactory);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void throwIfMapperFactoryReturnNullResponseMapperFacade() throws Exception {
        reset(mapperFactory);

        when(mapperFactory.getMapperFacade(KeyCli.class, Key.class)).thenReturn(requestMapper);
        when(mapperFactory.getMapperFacade(Value.class, ValueCli.class)).thenReturn(null);

        new KeyController(configService, mapperFactory);
    }

    private static class KeyCliGenerator implements EntityGenerator<KeyCli> {

        @Override
        public KeyCli generate() {
            return new KeyCli()
                    .setName(RANDOM.nextUuidString())
                    .setAttributes(
                            RANDOM.nextList(
                                    () -> new AttributeCli().setKey(RANDOM.nextUuidString()).setValue(RANDOM.nextUuidString())
                            )
                    );
        }
    }
}
