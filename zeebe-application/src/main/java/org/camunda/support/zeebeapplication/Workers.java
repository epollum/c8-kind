package org.camunda.support.zeebeapplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.client.api.response.ActivatedJob;
import io.camunda.zeebe.client.api.worker.JobClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import io.camunda.zeebe.spring.client.exception.ZeebeBpmnError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;


@Component
public class Workers {

    private static final Logger log = LoggerFactory.getLogger(Workers.class);

    @JobWorker(type = "foo") // autoComplete = true as default value
    public void handleFooJob(final ActivatedJob job) {
        logJob(job, null);
    }

    @JobWorker() // type is set to method name: "bar".
    public Map<String, Object> bar(final ActivatedJob job, @Variable String a) {
        logJob(job, a);
        return Collections.singletonMap("someResult", "42");
    }

    @JobWorker(type = "fail", fetchAllVariables = true)
    public void handleFailingJob(
            final JobClient client, final ActivatedJob job, @Variable String someResult) {
        logJob(job, someResult);
        throw new ZeebeBpmnError("DOESNT_WORK", "This will actually never work :-)", null);
    }

    private static void logJob(final ActivatedJob job, Object parameterValue) {
        log.info(
                "complete job\n>>> [type: {}, key: {}, element: {}, workflow instance: {}]\n{deadline; {}]\n[headers: {}]\n[variable parameter: {}\n[variables: {}]",
                job.getType(),
                job.getKey(),
                job.getElementId(),
                job.getProcessInstanceKey(),
                Instant.ofEpochMilli(job.getDeadline()),
                job.getCustomHeaders(),
                parameterValue,
                job.getVariables());
    }
}