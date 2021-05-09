package com.camunda.myBPM.deployBPM;

import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.junit.Test;

public class CheckStockDelegateTest {

	@Test
	public void verifyThatTheDelegateSetsVariable() {
		// set up class under test
		CheckStockDelegate delegate = new CheckStockDelegate();
		
		// set up mock of camunda execution environment
		DelegateExecution mockExecution = mock(DelegateExecution.class);
		
		// Run the delegate behaviour
		try {
			delegate.execute(mockExecution);
		} catch (Exception e) {
			e.printStackTrace();
			fail("throw exceoption: " + e.getMessage());
		}
		
		verify(mockExecution, times(1)).setVariable(eq("stockOK"), any(Boolean.class));
	}

}

