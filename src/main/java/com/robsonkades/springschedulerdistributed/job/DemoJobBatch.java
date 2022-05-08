package com.robsonkades.springschedulerdistributed.job;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.robsonkades.springschedulerdistributed.entity.Event;
import com.robsonkades.springschedulerdistributed.repository.EventRepository;

@Component
public class DemoJobBatch {

    private final TransactionTemplate transactionTemplate;
    private final EventRepository eventRepository;

    public DemoJobBatch(final EventRepository eventRepository, final TransactionTemplate transactionTemplate) {
        this.eventRepository = eventRepository;
        this.transactionTemplate = transactionTemplate;
    }

    @Scheduled(fixedDelay = 500, initialDelay = 5_000)
    public void execute() {
        AtomicInteger i = new AtomicInteger();
        while (i.get() < 10000) {
            transactionTemplate.execute(transactionStatus -> {
                List<Event> events = new ArrayList<>();
                for (int j = 0; j < 50; j++) {
                    events.add(new Event(UUID.randomUUID().toString(), UUID.randomUUID().toString()));
                }
                eventRepository.saveAll(events);
                i.getAndIncrement();
                return true;
            });
        }
    }
}
