package ru.necs.spi.controller;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.net.ConnectException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.testng.annotations.Test;
import ru.necs.domain.spring.DomainConfig;
import ru.necs.web.controller.spi.SPIConfig;

@Test
public class HelloControllerTest {

	static {
		System.setProperty("spring.profiles.active", "h2");
	}

	@Test(expectedExceptions = ConnectException.class)
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
		} catch (ConnectException e) {
			assertEquals(e.getMessage(), "Connection refused: connect");
		}
	}
}
