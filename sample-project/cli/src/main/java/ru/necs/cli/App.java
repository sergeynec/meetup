package ru.necs.cli;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.necs.cli.model.KeyCli;
import ru.necs.cli.service.KeyController;
import ru.necs.cli.spring.CliConfig;

public class App {

    public static void main(final String[] args) {
        try (final AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(CliConfig.class)) {
            final KeyController controller = applicationContext.getBean(KeyController.class);

            System.out.println(
                    controller.lookup(
                            new KeyCli()
                                    .setName(args[0])
                    )
            );
        }
    }
}
