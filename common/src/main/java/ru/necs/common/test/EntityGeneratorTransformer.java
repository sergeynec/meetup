package ru.necs.common.test;

import static org.slf4j.LoggerFactory.getLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.slf4j.Logger;
import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

public class EntityGeneratorTransformer implements IAnnotationTransformer {

    private static final Logger LOGGER = getLogger(EntityGeneratorTransformer.class);

    @Override
    public void transform(final ITestAnnotation annotation,
                          final Class clazz,
                          final Constructor constructor,
                          final Method testMethod) {
        if (testMethod == null || !testMethod.isAnnotationPresent(EntityGenerators.class) || hasCustomDataProvider(annotation)) {
            return;
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Inject {} data provider", EntitiesDataProvider.class.getName());
        }

        annotation.setDataProviderClass(EntitiesDataProvider.class);
        annotation.setDataProvider("generate");
    }

    private boolean hasCustomDataProvider(final ITestAnnotation annotation) {
        return annotation.getDataProvider() != null && !annotation.getDataProvider().equals("")
                || annotation.getDataProviderClass() != null;
    }
}
