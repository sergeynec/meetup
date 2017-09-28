package ru.necs.common.test;

import static java.lang.String.format;
import static org.slf4j.LoggerFactory.getLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.testng.annotations.DataProvider;

public class EntitiesDataProvider {

    private static final Logger LOGGER = getLogger(EntitiesDataProvider.class);

    private EntitiesDataProvider() {
    }

    @DataProvider
    public static Object[][] generate(final Method method) {
        final EntityGenerators annotation = method.getAnnotation(EntityGenerators.class);
        if (annotation == null) {
            throw new IllegalStateException(
                    format("Must be used together with the %s", EntityGenerators.class.getName())
            );
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Invocation count: {}", annotation.invocationCount());

            for (final Class<? extends EntityGenerator> generatorClass : annotation.value()) {
                LOGGER.debug("Registered entity generator [{}]", generatorClass.getName());
            }
        }

        final Object[][] result = new Object[annotation.invocationCount()][];
        for (int i = 0; i < annotation.invocationCount(); i++) {
            final List generatorsResult = new ArrayList();
            for (final Class<? extends EntityGenerator> generatorClass : annotation.value()) {
                generatorsResult.add(((EntityGenerator<?>) getGeneratorInstance(generatorClass)).generate());
            }

            result[i] = generatorsResult.toArray();
        }

        return result;
    }

    private static EntityGenerator getGeneratorInstance(final Class<? extends EntityGenerator> generatorClass) {
        try {
            final Constructor<? extends EntityGenerator> constructor = generatorClass.getDeclaredConstructor();
            constructor.setAccessible(true);

            return constructor.newInstance();
        } catch (final Exception e) {
            throw new IllegalStateException();
        }
    }
}
