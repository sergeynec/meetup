package ru.necs.common.test;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(METHOD)
@Retention(RUNTIME)
public @interface EntityGenerators {

    Class<? extends EntityGenerator>[] value() default {};

    int invocationCount() default 10;
}
