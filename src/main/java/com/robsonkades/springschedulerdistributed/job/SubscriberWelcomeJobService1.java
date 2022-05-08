package com.robsonkades.springschedulerdistributed.job;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.support.TransactionTemplate;

//@Component
public class SubscriberWelcomeJobService1 {

    private final ExecuteJob executeJob;
    private final TransactionTemplate transactionTemplate;

    public SubscriberWelcomeJobService1(final ExecuteJob executeJob, final TransactionTemplate transactionTemplate) {
        this.executeJob = executeJob;
        this.transactionTemplate = transactionTemplate;
    }

    @Scheduled(fixedDelay = 500, initialDelay = 5_000)
    public void execute() {
        transactionTemplate.execute(transactionStatus -> {
            executeJob.execute();
            return true;
        });
    }
}
