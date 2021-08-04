package org.projectzion.game.mmoconnector.batch;

import org.projectzion.game.mmoconnector.persistence.entities.rpc.UserCall;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class UserCallBatchConfig {
    private static final Logger log = LoggerFactory.getLogger(UserCallBatchConfig.class);

    @Autowired
    private JobOperator operator;

    @Autowired
    private JobExplorer jobs;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public UserCallJobExecutionListener userCallJobExecutionListener;

    @Autowired
    JobRegistry jobRegistry;

    @Bean
    public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor() {
        JobRegistryBeanPostProcessor postProcessor = new JobRegistryBeanPostProcessor();
        postProcessor.setJobRegistry(jobRegistry);
        return postProcessor;
    }

    @Scheduled(fixedRate = 5000)
    public void run() throws Exception {
        List<JobInstance> lastInstances = jobs.getJobInstances("userCallJob", 0, 1);
        if (lastInstances.isEmpty()) {
            JobParameters parameters = new JobParameters();
            JobParameter date = new JobParameter(new Date());
            parameters.getParameters().put("date", date);
            JobParameter time = new JobParameter(System.currentTimeMillis());
            parameters.getParameters().put("time", time);

            jobLauncher.run(userCallJob(userCallJobExecutionListener, keinStep()), parameters);

        } else {
            operator.startNextInstance("userCallJob");
        }
    }

    @Bean
    public Job userCallJob(UserCallJobExecutionListener listener, Step keinStep) {
        return jobBuilderFactory.get("userCallJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(keinStep)
                .end()
                .build();
    }

    @Bean
    public Step keinStep() {
        return stepBuilderFactory.get("keinStep")
                .<UserCall, UserCall>chunk(10)
                .reader(getUserCallReader())
                .processor(getUserCallProcessor())
                .writer(getUserCallWriter())
                .build();
    }

    @Bean
    UserCallReader getUserCallReader() {
        return new UserCallReader();
    }

    @Bean
    UserCallProcessor getUserCallProcessor() {
        return new UserCallProcessor();
    }

    @Bean
    UserCallWriter getUserCallWriter(){
        return new UserCallWriter();
    }
}
