package ru.necs.spi.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.testng.annotations.Test;
import ru.necs.domain.spring.DomainConfig;
import ru.necs.web.controller.spi.SPIConfig;
import ru.necs.web.controller.spi.SPIController;

@Test
public class SPIControllerTest {

	static {
		System.setProperty("spring.profiles.active", "h2");
	}

	@Test(expectedExceptions = SocketException.class)
	public void checkSPILoadingWithoutLibrary() throws Exception {
		AbstractApplicationContext context = new AnnotationConfigApplicationContext(SPIConfig.class,
				DomainConfig.class);
		try (Socket s = new Socket("localhost", 7777)) {
		}
		context.close();
	}

	@Test
	public void checkSPILoadingLibrary() throws Exception {
		File socketJarFile = new File("src/test/resources/socket.jar");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.setClassLoader(
				new URLClassLoader(new URL[] { socketJarFile.toURI().toURL() }, ClassLoader.getSystemClassLoader()));
		context.register(DomainConfig.class, SPIConfig.class);
		context.refresh();
		try (Socket s = new Socket("localhost", 7777)) {
		}
		context.close();
		try {
			try (Socket s = new Socket("localhost", 7777)) {
				fail("Must failed on Connection refused: connect exception");
			}
		} catch (SocketException e) {
			// we must get that exception
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void checkSPIImplLoad() throws Exception {
		try (AbstractApplicationContext context = new AnnotationConfigApplicationContext(SPIConfig.class,
				DomainConfig.class)) {
			List<SPIController> controllers = (List<SPIController>) context.getBean("getSpiController");
			assertTrue(controllers.isEmpty());
		}
		try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();) {
			context.setClassLoader(
					new URLClassLoader(new URL[] { new File("src/test/unloadableResources").toURI().toURL() },
							ClassLoader.getSystemClassLoader()));
			context.register(DomainConfig.class, SPIConfig.class);
			context.refresh();
			List<SPIController> controllers = (List<SPIController>) context.getBean("getSpiController");
			assertEquals(1, controllers.size());
		}
	}
}
