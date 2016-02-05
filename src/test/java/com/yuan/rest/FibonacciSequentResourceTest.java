package com.yuan.rest;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.util.runner.ConcurrentRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.yuan.fib.FibonacciSequence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(ConcurrentRunner.class)
public class FibonacciSequentResourceTest extends JerseyTest {

	@Override
	protected ResourceConfig configure() {
		enable(TestProperties.LOG_TRAFFIC);
		return new ResourceConfig(FibonacciSequenceResource.class);
	}

	@Test
	public void testConnection() {
		Response response = target().path(App.ROOT_PATH + "/10")
				.request("text/plain").get();
		assertEquals(200, response.getStatus());
		response = target().path(App.ROOT_PATH + "/abc").request("text/plain")
				.get();
		assertEquals(400, response.getStatus());
		response = target().path(App.ROOT_PATH + "/-10").request("text/plain")
				.get();
		assertEquals(400, response.getStatus());
		response = target().path(App.ROOT_PATH + "/10000000")
				.request("text/plain").get();
		assertEquals(413, response.getStatus());
	}

	private void testClientStringResponse(int seqsize) {
		String s = target().path(App.ROOT_PATH + "/" + seqsize).request()
				.get(String.class);
		assertEquals(new FibonacciSequence(seqsize).generateSequence()
				.generatePrettyPrintString(), s);

		assertEquals(s.trim().split(" ").length, seqsize);
	}

	@Test
	public void testClientStringResponse() {
		testClientStringResponse(10);
		testClientStringResponse(100);
		testClientStringResponse(1024);
		testClientStringResponse(747);
		testClientStringResponse(8);
		testClientStringResponse(20);
		testClientStringResponse(70);

	}

	@Test
	public void testHead() {
		Response response = target().path(App.ROOT_PATH + "/10").request()
				.head();
		assertEquals(200, response.getStatus());
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
	}
	

	@Test
	public void testFooBarOptions() {
		Response response = target().path(App.ROOT_PATH + "/10").request()
				.header("Accept", "foo/bar").options();
		assertEquals(200, response.getStatus());
		final String allowHeader = response.getHeaderString("Allow");
		_checkAllowContent(allowHeader);
		assertEquals("foo/bar", response.getMediaType().toString());
		assertEquals(0, response.getLength());
	}

	@Test
	public void testTextPlainOptions() {
		Response response = target().path(App.ROOT_PATH + "/10").request()
				.header("Accept", MediaType.TEXT_PLAIN).options();
		assertEquals(200, response.getStatus());
		final String allowHeader = response.getHeaderString("Allow");
		_checkAllowContent(allowHeader);
		assertEquals(MediaType.TEXT_PLAIN_TYPE, response.getMediaType());
		final String responseBody = response.readEntity(String.class);
		_checkAllowContent(responseBody);
	}

	private void _checkAllowContent(final String content) {
		assertTrue(content.contains("GET"));
		assertTrue(content.contains("HEAD"));
		assertTrue(content.contains("OPTIONS"));
	}

}
