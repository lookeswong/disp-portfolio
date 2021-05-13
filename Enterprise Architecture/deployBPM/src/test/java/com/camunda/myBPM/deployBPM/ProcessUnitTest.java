package com.camunda.myBPM.deployBPM;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.bpmn.TaskAssert;
import org.camunda.bpm.engine.test.mock.Mocks;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class ProcessUnitTest {

	static {
		LogFactory.useSlf4jLogging(); // MyBatis
	}

	@ClassRule
	@Rule
	public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

	private static final String PROCESS_DEFINITION_KEY = "deployBPM";

	static {
		LogFactory.useSlf4jLogging(); // set up the use of logging
	}

	// set up the fixture that will run before each
	@Before
	public void setup() {
		init(rule.getProcessEngine());
	}

	// test if process is deployable
	@Test
	@Deployment(resources = "process.bpmn")
	public void testParsingAndDeployment() {
		// just for deployment
	}

//	// test if service tasks between is working or not
	@Test
	@Deployment(resources = "process.bpmn")
	public void testCurrentStatus() {
		// Obtain test run of BPMN
		ProcessInstanceWithVariables processInstance = (ProcessInstanceWithVariables) processEngine()
				.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

		// Obtain the value of the stockOK variable
		boolean stockOK = (boolean) processInstance.getVariables().get("stockOK");
		System.out.println("stockOK" + stockOK);

		// Obtain a reference to the current task
		TaskAssert task = assertThat(processInstance).task();

		if (stockOK) {
			assertThat(processInstance).isWaitingAt("Activity_12a72z8");
			task.hasName("Deliver Item");
			task.isNotAssigned();
		} else {
			assertThat(processInstance).isWaitingAt("Activity_1752lvf");
			task.hasName("Reorder stock");
			task.isNotAssigned();
		}
	}

	@Test
	@Deployment(resources = "process.bpmn")
	public void testCompletionOftask() {
		// Obtain test run of BPMN
		ProcessInstanceWithVariables processInstance = (ProcessInstanceWithVariables) processEngine()
				.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

		// Obtain the value of the stockOK variable
		boolean stockOK = (boolean) processInstance.getVariables().get("stockOK");
		System.out.println("stockOK" + stockOK);

		// Obtain a reference to the current task
		TaskAssert task = assertThat(processInstance).task();

		if (stockOK) {
			complete(task("Activity_12a72z8"));
		} else {
			complete(task("Activity_1752lvf"));
		}

		assertThat(processInstance).isEnded();
	}
	


}
