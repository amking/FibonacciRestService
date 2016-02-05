package com.yuan.fib.cache;

import java.math.BigInteger;
import java.util.ArrayList;

public interface IFibSequenceCache {
	
	public void initialCacheSystem();
	
	public ArrayList<BigInteger> loadFibonacciSequenceFragment(String cacheKey);
	
	public void storeFibonacciSequenceFragment(String fragKey, ArrayList<BigInteger> fibnocciSequenceFrag);
}
