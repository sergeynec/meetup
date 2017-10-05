package ru.necs.spi.controller;

import static org.testng.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.SocketException;
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

    @Test(expectedExceptions=SocketException.class)
    public void checkSPILoadingWithoutLibrary() throws Exception{
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(SPIConfig.class,
				DomainConfig.class);
    	try(Socket s= new Socket("localhost", 7777)){}
		context.close();
    }
    
  
    
    @Test
    public void checkSPILoadingLibrary() throws Exception{
    	File socketJarFile = null;
    	addLibrary(socketJarFile.toURI().toURL());
    	AbstractApplicationContext context = new AnnotationConfigApplicationContext(SPIConfig.class,
				DomainConfig.class);
    	try(Socket s = new Socket("localhost", 7777)){
    	assertTrue(s.isConnected());
		context.close();
		assertTrue(s.isClosed());
    	}
    }
    
    private static void addLibrary(URL url) throws IOException {
    	URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
    	  Class<URLClassLoader> sysclass = URLClassLoader.class;

    	  try {
    	     Method method = sysclass.getDeclaredMethod("addURL", new Class[] {URL.class});
    	     method.setAccessible(true);
    	     method.invoke(sysloader, new Object[]{url});
    	  } catch (Throwable t) {
    	     t.printStackTrace();
    	     throw new IOException("Error, could not add URL to system classloader");
    	  }
    }
}
