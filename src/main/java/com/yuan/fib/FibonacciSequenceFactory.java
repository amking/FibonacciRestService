package com.yuan.fib;

import java.math.BigInteger;
import java.util.ArrayList;

import com.yuan.fib.cache.IFibSequenceCache;
import com.yuan.fib.cache.InMemoryFibSequenceCache;

public class FibonacciSequenceFactory {

	private static final BigInteger FIB_SEQ_FIRST_ELEMENT = BigInteger.ZERO;
	private static final BigInteger FIB_SEQ_SECOND_ELEMENT = BigInteger.ONE;

	// We will use the in-memory cache to store the fibonacci sequence fragment,
	// each fragment has 100 elements
	IFibSequenceCache fibSeqCache = new InMemoryFibSequenceCache();
	private static final int FIB_CACHE_FRAGMENT_SIZE = 100;

	private static FibonacciSequenceFactory factory = new FibonacciSequenceFactory();

	/**
	 * Factory method to generate the fibonacciSequence with specified size
	 * 
	 * @param seqSize
	 *            : fibonacci sequence size
	 * @return new generated FibonacciSequence
	 */
	public static FibonacciSequence createFibonacciSequence(int seqSize) {
		return factory.generateFibonacciSequence(seqSize);
	}

	/**
	 * Generate fibonacci sequence with given sequence size
	 * 
	 * @param seqSize
	 * @return
	 */
	private FibonacciSequence generateFibonacciSequence(int seqSize) {

		// Sequence size should be greater than zero
		if (seqSize <= 0) {
			return null;
		}

		// new generated fibonacci sequence, the sequence size is specified by
		// input argument
		FibonacciSequence fibSeq = new FibonacciSequence(seqSize);

		// Before calculating the fibonacci sequence, we will check whether
		// there are some cached fragments usable
		int fragmentsCount = seqSize / FIB_CACHE_FRAGMENT_SIZE;

		// check the in-memory cache, use the cached fragments if possible
		for (int fragmentIdx = 0; fragmentIdx < fragmentsCount; fragmentIdx++) {
			String cacheKey = "FibSeq" + fragmentIdx;

			// Get cached fragment
			ArrayList<BigInteger> cachedFrag = fibSeqCache
					.loadFibonacciSequenceFragment(cacheKey);

			// Once cache is missed, we need to compute and cache the fragment
			if (cachedFrag == null) {
				cachedFrag = generateFibonacciSequenceFragment(fragmentIdx
						* FIB_CACHE_FRAGMENT_SIZE, FIB_CACHE_FRAGMENT_SIZE,
						fibSeq);
				fibSeqCache
						.storeFibonacciSequenceFragment(cacheKey, cachedFrag);
			}

			// Add the fragment in the sequence
			fibSeq.getFibonacciNumberArray().addAll(cachedFrag);
		}

		// The cached fragment size is constant value, so we may need to compute
		// the remaining fragment and append it into the generated fibonacci
		// sequence
		int uncachedElementSize = seqSize % FIB_CACHE_FRAGMENT_SIZE;
		if (uncachedElementSize > 0) {
			ArrayList<BigInteger> unCachedFragment = generateFibonacciSequenceFragment(
					fragmentsCount * FIB_CACHE_FRAGMENT_SIZE,
					uncachedElementSize, fibSeq);
			fibSeq.getFibonacciNumberArray().addAll(unCachedFragment);
		}

		return fibSeq;
	}

	/**
	 * Generate the fibonacci sequence fragment by computing The formula is:
	 * f(x) = f(x-1) + f(x-2)
	 * 
	 * @param startPos
	 *            : The start position for the fibonacci sequence fragment
	 * @param fragmentSize
	 *            : fragment size
	 * @param fibSeq
	 *            : The fibonacci sequence object, it might be useful to compute
	 *            the first two elements in the fragment.
	 * @return
	 */
	private ArrayList<BigInteger> generateFibonacciSequenceFragment(
			int startPos, int fragmentSize, FibonacciSequence fibSeq) {

		// New generated fibonacci sequence fragment
		ArrayList<BigInteger> fragment = new ArrayList<BigInteger>(fragmentSize);

		int fragIdx = startPos;

		/**
		 * Generate the fibonacci number one by one
		 */
		for (int i = 0; i < fragmentSize; i++) {

			// Fibonacci number can be a very large integer, so use BigInteger
			// to hold the new generated fibonacci number
			BigInteger newFibNumber = null;

			// f(0) = 0
			if (fragIdx == 0) {
				newFibNumber = FIB_SEQ_FIRST_ELEMENT;
			}
			// f(1) = 1
			else if (fragIdx == 1) {
				newFibNumber = FIB_SEQ_SECOND_ELEMENT;
			} else if (i == 0) {
				newFibNumber = fibSeq
						.getFibonacciNumberArray()
						.get(startPos - 1)
						.add(fibSeq.getFibonacciNumberArray().get(startPos - 2));
			} else if (i == 1) {
				newFibNumber = fibSeq.getFibonacciNumberArray()
						.get(startPos - 1).add(fragment.get(0));
			} else {
				newFibNumber = fragment.get(i - 1).add(fragment.get(i - 2));
			}

			fragment.add(newFibNumber);
			fragIdx++;
		}
		return fragment;

	}

}
