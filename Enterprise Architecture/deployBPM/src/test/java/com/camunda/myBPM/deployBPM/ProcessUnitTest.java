package com.camunda.myBPM.deployBPM;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.bpmn.TaskAssert;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

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
  
  @Test
  @Deployment(resources="process.bpmn")
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
			  .getRuntimeService()
			  .startProcessInstanceByKey(PROCESS_DEFINITION_KEY);
	  
	  // Obtain a reference to the current task
	  TaskAssert taskAssert = assertThat(processInstance).task();
	  
	  TaskEntity task = (TaskEntity) taskAssert.getActual();
	  
	  task.delegate("user");
	  
	  task.resolve();
	  
//	  assertThat(processInstance).isEnded();
  }
  
  
  
//  @Test
//  @Deployment(resources = "process.bpmn")
//  public void testHappyPath() {
//    // Drive the process by API and assert correct behavior by camunda-bpm-assert
//    ProcessInstance processInstance = processEngine().getRuntimeService()
//        .startProcessInstanceByKey(ProcessConstants.PROCESS_DEFINITION_KEY);
//
////    assertThat(processInstance).isEnded();
//  }

}
