package com.yuan.fib;

import static org.junit.Assert.*;

import org.junit.Test;

public class FibonacciSequenceTest {

	public void testFibonacciSequenceGenerator(int seqSize) {
		FibonacciSequence generatedFibSeq = FibonacciSequenceFactory
				.createFibonacciSequence(seqSize);
		FibonacciSequence nativeFibSeq = new FibonacciSequence(seqSize);
		assertEquals(generatedFibSeq.generatePrettyPrintString(), nativeFibSeq
				.generateSequence().generatePrettyPrintString());
		assertEquals(generatedFibSeq.getFibonacciNumberArray().size(), seqSize);
	}

	@Test
	public void testComputeFibonacciSequence() {

		FibonacciSequence fibSeq = FibonacciSequenceFactory
				.createFibonacciSequence(0);
		assertEquals(fibSeq, null);

		fibSeq = FibonacciSequenceFactory.createFibonacciSequence(-1);
		assertEquals(fibSeq, null);

		testFibonacciSequenceGenerator(150);

		testFibonacciSequenceGenerator(1);

		testFibonacciSequenceGenerator(2);

		testFibonacciSequenceGenerator(26);

		testFibonacciSequenceGenerator(100);

		testFibonacciSequenceGenerator(150);

		testFibonacciSequenceGenerator(120);

		testFibonacciSequenceGenerator(10);
	}

}
