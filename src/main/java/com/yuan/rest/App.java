package com.yuan.rest;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.grizzly.http.server.HttpServer;

/**
 * App to launch the restful service for fibonacci sequence!
 */
public class App {

	private static final URI BASE_URI = URI.create("http://127.0.0.1:8080/");
	public static final String ROOT_PATH = "Sequences";

	public static void main(String[] args) {
		try {
			System.out
					.println("Fibnocci Sequence Restful Service is Starting ...");

			final ResourceConfig resourceConfig = new ResourceConfig(
					FibonacciSequenceResource.class);
			final HttpServer server = GrizzlyHttpServerFactory
					.createHttpServer(BASE_URI, resourceConfig, false);
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
				@Override
				public void run() {
					server.shutdownNow();
				}
			}));
			server.start();

			System.out
					.println(String
							.format("Application started.\nTry out %s%s\nStop the application using CTRL+C",
									BASE_URI, ROOT_PATH));
			Thread.currentThread().join();
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
