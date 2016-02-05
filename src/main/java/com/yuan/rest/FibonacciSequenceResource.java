package com.yuan.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.yuan.fib.FibonacciSequence;
import com.yuan.fib.FibonacciSequenceFactory;

/**
 *
 * @author Yuan
 */
@Path("/Sequences")
public class FibonacciSequenceResource {

	@GET
	@Path("/{seqSizeStr}")
	// @Produces("MediaType.APPLICATION_JSON")
	// @Produces("plain/text")
	public Response getFibSequence(@PathParam("seqSizeStr") String seqSizeStr) {

		int seqSize = 0;

		/**
		 * Check whether the path is valid
		 */
		boolean isPathValid = true;
		try {
			seqSize = Integer.parseInt(seqSizeStr);
		} catch (NumberFormatException e) {
			isPathValid = false;
		}
		// It will be treated as invalid path if the sequence end position is
		// less than zero,
		isPathValid = isPathValid && (seqSize >= 0);

		/**
		 * The response code is 400 due to invalid input
		 */
		if (!isPathValid) {
			ResponseBuilder builder = Response.status(400);
			return builder.build();
		}

		/**
		 * The service can only compute the fibonacci sequence within 10000
		 * numbers, If the request size is greater than 10000, the service will
		 * return 413 (Request Entity Too Large) error code
		 */
		if (seqSize > 10000) {
			ResponseBuilder builder = Response.status(413);
			return builder.build();
		}

		// compute the fibonacci sequence string
		FibonacciSequence fibSeq = FibonacciSequenceFactory
				.createFibonacciSequence(seqSize);
		String fibSeqStr = fibSeq.generatePrettyPrintString();

		// generate and return ok response
		return generateOkResponse(fibSeqStr);
	}

	/**
	 * Generate the ok response to the customer, For the performance
	 * consideration, enable the cache control in the client side.
	 * 
	 * @param responseContent
	 * @return
	 */
	private Response generateOkResponse(String responseContent) {
		ResponseBuilder builder = Response.ok(responseContent);
		CacheControl cc = new CacheControl();
		cc.setMaxAge(86400);
		return builder.cacheControl(cc).type(MediaType.TEXT_PLAIN).build();
	}

}
