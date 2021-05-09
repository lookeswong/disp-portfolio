package com.camunda.myBPM.deployBPM;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.variable.Variables;
import org.junit.Rule;
import org.junit.Test;

import com.icegreen.greenmail.junit.GreenMailRule;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

public class SendMailConnectorTest {
	
	@Rule
	public ProcessEngineRule engineRule = new ProcessEngineRule();
	
	@Rule
	public final GreenMailRule greenMail = new GreenMailRule(ServerSetupTest.ALL);
	
	@Test
	@Deployment(resources = "process.bpmn")
	public void sendMailWithTextBody() throws MessagingException {
		engineRule.getRuntimeService().startProcessInstanceByKey("deployBPM");

	    MimeMessage[] mails = greenMail.getReceivedMessages();
	    assertThat(mails).hasSize(1);

	    MimeMessage mail = mails[0];
	    assertThat(mail.getSubject()).isEqualTo("Test");
	    assertThat(GreenMailUtil.getBody(mail)).isEqualTo("text body");
	}

}
