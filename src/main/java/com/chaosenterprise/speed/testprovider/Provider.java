package com.chaosenterprise.speed.testprovider;

import com.chaosenterprise.speed.data.TestData;

public interface Provider {

	public boolean testConnection();

	public void runTest();

	public TestData getTestResult();

	public void close();
}
