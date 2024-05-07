package org.camunda.support.zeebeapplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ProcessInstanceEvent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import io.camunda.zeebe.client.impl.ZeebeClientBuilderImpl;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PeriodicProcessStarter {

    private static Logger log = LoggerFactory.getLogger(PeriodicProcessStarter.class);

    @Autowired
    private ZeebeClient zeebeClient;

//    @PostConstruct
//    public void deployAllProcesses() throws IOException, URISyntaxException {
//        log.info("deploying the following resources:");
//        List<String> bpmns = List.of("usertask.bpmn","foobar.bpmn");
//        bpmns.forEach(item -> log.info(item));
//        zeebeClient.newDeployResourceCommand().addResourceFromClasspath("usertask.bpmn").send().join();
//    }

    @Scheduled(fixedRate = 1L)
    public void startUserTaskProcesses() {
        final ProcessInstanceEvent event =
                zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId("Process_UserTask")
                        .latestVersion()
                        .send()
                        .join();

        logInstance(event);
    }

    @Scheduled(fixedRate = 1L)
    public void startFooBarProcess(){
        final ProcessInstanceEvent event =
                zeebeClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId("Process_FooBar")
                        .latestVersion()
                        .variables(
                                "{\"a\": \""
                                        + UUID.randomUUID().toString()
                                        + "\",\"b\": \""
                                        + new Date().toString()
                                        + "\"}")
                        .send()
                        .join();

        logInstance(event);
    }

    private void logInstance(ProcessInstanceEvent event){
        log.info(
                "started instance for workflowKey='{}', bpmnProcessId='{}', version='{}' with workflowInstanceKey='{}'",
                event.getProcessDefinitionKey(),
                event.getBpmnProcessId(),
                event.getVersion(),
                event.getProcessInstanceKey());
    }
}
