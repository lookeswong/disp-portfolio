package com.camunda.myBPM.deployBPM;

import java.util.Random;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class RetryPaymentDelegate implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		// TODO Auto-generated method stub
		Random rando = new Random();
		execution.setVariable("retryPaymentOK", rando.nextBoolean());
	}

}
