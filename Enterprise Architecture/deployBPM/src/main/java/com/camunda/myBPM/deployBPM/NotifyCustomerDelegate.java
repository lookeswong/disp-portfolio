package com.camunda.myBPM.deployBPM;

import java.util.logging.Logger;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class NotifyCustomerDelegate implements JavaDelegate {
	
	private final static Logger LOGGER = Logger.getLogger("ORDER-CANCEL");

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		LOGGER.info("Processing request");
	}

}
