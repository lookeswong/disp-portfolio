This directory is a represents the DISP Portfolio.

The directory contains: 
-a file of named Description of Enterprise and Information which is the Zachaman Framework of Information Systems Architecture and a brief description of the enterprise and the role of the information
systems

-a basic business process BPMN diagram

-a elaborate business process BPMN diagram

-a deployed business process guide word document which guide you through the deployment of the business process

-a folder named deployment images which consists of the screenshot of the deployment diagram

-a Java project named deployBPM

***** camunda-bpm-mail *****

To enable this in your pc, please download camunda-bpm-mail-core.jar into your tomcat/server/apache/lib and paste the mail-config.properties file under your tomcat/server/apache/conf.

You will also need to download javax.mail, camunda-connect-cor and slf4j-api. Detailed guide follow this link:
https://github.com/camunda-community-hub/camunda-bpm-mail#how-to-configure-it

After that include this dependency below in your pom.xml
<dependency>
	<groupId>org.camunda.bpm.extension</groupId>
	<artifactId>camunda-bpm-mail-core</artifactId>
	<version>1.2.0</version>
</dependency>

<dependency>
	<groupId>javax.activation</groupId>
	<artifactId>activation</artifactId>
	<version>1.1.1</version>
</dependency>



Test Cases:
*****CheckStockDelegateTest.java****
verifyThatTheDelegateSetsVariable() :- test if the Check Stock Service tasks can successfully generate a boolean variable

*****MailProviderIntegrationTest.java*****
notificationService() :- test if the send-mail connector used in Notify User service task is able to send email to the email address

*****ProcessUnitTest.java*****
testCurrentStatus() :- test the status of the user tasks

testCompletionOftask() :- test if the business process can be finish successfully or not

testParsingAndDeployment() :- test if the business process is deployable.
