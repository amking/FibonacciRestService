package com.yuan.fib.cache;

import java.math.BigInteger;
import java.util.ArrayList;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class InMemoryFibSequenceCache implements IFibSequenceCache {

	private Cache<String, ArrayList<BigInteger>> fibNumberArrayCache = null;

	@Override
	public ArrayList<BigInteger> loadFibonacciSequenceFragment(String cacheKey) {
		// TODO: find a better method name, this method should be named like
		// checkCacheStatusAndInitIfNeccessary
		initialCacheSystem();
		return fibNumberArrayCache.getIfPresent(cacheKey);
	}

	@Override
	public void storeFibonacciSequenceFragment(String cacheKey,
			ArrayList<BigInteger> fibnocciSequenceFrag) {
		initialCacheSystem();
		fibNumberArrayCache.put(cacheKey, fibnocciSequenceFrag);
	}

	@Override
	public void initialCacheSystem() {
		if (fibNumberArrayCache == null) {
			synchronized (this) {
				if (fibNumberArrayCache == null)
					fibNumberArrayCache = CacheBuilder.newBuilder().build();
			}
		}
	}

}
