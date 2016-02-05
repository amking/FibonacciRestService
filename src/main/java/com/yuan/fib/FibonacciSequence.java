package com.yuan.fib;

import java.util.ArrayList;
import java.math.BigInteger;

public class FibonacciSequence {

	private static final BigInteger FIB_SEQ_FIRST_ELEMENT = BigInteger.ZERO;
	private static final BigInteger FIB_SEQ_SECOND_ELEMENT = BigInteger.ONE;

	private int sequenceSize = 0;

	public int getSequenceSize() {
		return sequenceSize;
	}

	private ArrayList<BigInteger> fibonacciNumberArray = null;

	public ArrayList<BigInteger> getFibonacciNumberArray() {
		return fibonacciNumberArray;
	}

	public FibonacciSequence(int sequenceSize) {
		this.sequenceSize = sequenceSize;
		fibonacciNumberArray = new ArrayList<BigInteger>(sequenceSize);
	}

	private boolean hasAlreadyGenerated() {
		return (fibonacciNumberArray.size() == sequenceSize);
	}

	public String generatePrettyPrintString() {
		if (!hasAlreadyGenerated()) {
			this.generateSequence();
		}

		// generate and return readable string
		StringBuffer fibSeqSB = new StringBuffer();
		for (BigInteger fibNumber : fibonacciNumberArray) {
			fibSeqSB.append(fibNumber).append(" ");
		}
		fibSeqSB.deleteCharAt(fibSeqSB.length() - 1);
		return fibSeqSB.toString();
	}

	// generate the fibonacci sequence directly
	public FibonacciSequence generateSequence() {

		// Check whether all the items have already created
		if (hasAlreadyGenerated()) {
			return this;
		}

		if (sequenceSize < 1) {
			return this;
		}

		// The first two numbers in fibonacci sequence is 0 and 1
		fibonacciNumberArray.add(FIB_SEQ_FIRST_ELEMENT);
		if (sequenceSize >= 2) {
			fibonacciNumberArray.add(FIB_SEQ_SECOND_ELEMENT);
		}

		// Generate the fibonacci number one by one, access the pre-computed
		// number in the array to get rid of re-computation
		for (int numPos = 2; numPos < sequenceSize; numPos++) {
			BigInteger fibNum = fibonacciNumberArray.get(numPos - 1).add(
					fibonacciNumberArray.get(numPos - 2));
			fibonacciNumberArray.add(fibNum);
		}

		// generate and return readable string
		StringBuffer fibSeqSB = new StringBuffer();
		for (BigInteger fibNumber : fibonacciNumberArray) {
			fibSeqSB.append(fibNumber).append(" ");
		}
		fibSeqSB.deleteCharAt(fibSeqSB.length() - 1);

		return this;
	}

}
