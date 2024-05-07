package org.camunda.support.zeebeapplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.command.DeployResourceCommandStep1;
import io.camunda.zeebe.client.api.response.*;
import io.camunda.zeebe.process.test.api.ZeebeTestEngine;
import io.camunda.zeebe.process.test.assertions.*;
import io.camunda.zeebe.process.test.extension.testcontainer.ZeebeProcessTest;
import io.camunda.zeebe.process.test.filters.RecordStream;
import io.camunda.zeebe.spring.client.annotation.Deployment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@ZeebeProcessTest
class ZeebeApplicationTests {
	private ZeebeTestEngine engine;
	private ZeebeClient client;
	private RecordStream recordStream;

	@BeforeEach
	void deployProcesses() {
		// The embedded engine is completely reset before each test run.

		// Therefore, we need to deploy the process each time
		final DeploymentEvent deploymentEvent =
				deployResources("foobar.bpmn");

		BpmnAssert.assertThat(deploymentEvent)
				.containsProcessesByResourceName(
						"foobar.bpmn");
	}

	@Test
	void fooServiceTest(){
		// Create the process instance
		ProcessInstanceEvent event = client.newCreateInstanceCommand()
				.bpmnProcessId("Process_FooBar")
				.latestVersion()
				.send()
				.join();

		//get job
		ActivateJobsResponse response = client.newActivateJobsCommand()
				.jobType("foo")
				.maxJobsToActivate(1)
				.send()
				.join();
		ActivatedJob activatedJob = response.getJobs().get(0);
		//complete job
		client.newCompleteCommand(activatedJob)
				.send()
				.join();

		ProcessInstanceAssert assertion = BpmnAssert.assertThat(event)
				.isActive()
				.hasPassedElement("Activity_Foo")
				.isWaitingAtElements("Activity_Bar");
	}

	private DeploymentEvent deployResources(final String... resources) {
		final DeployResourceCommandStep1 commandStep1 = client.newDeployResourceCommand();

		DeployResourceCommandStep1.DeployResourceCommandStep2 commandStep2 = null;
		for (final String process : resources) {
			if (commandStep2 == null) {
				commandStep2 = commandStep1.addResourceFromClasspath(process);
			} else {
				commandStep2 = commandStep2.addResourceFromClasspath(process);
			}
		}

		return commandStep2.send().join();
	}

}
