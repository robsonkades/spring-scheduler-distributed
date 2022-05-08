package com.robsonkades.springschedulerdistributed.job;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.robsonkades.springschedulerdistributed.entity.Event;
import com.robsonkades.springschedulerdistributed.entity.Scheduler;
import com.robsonkades.springschedulerdistributed.entity.Subscriber;
import com.robsonkades.springschedulerdistributed.repository.EventRepository;
import com.robsonkades.springschedulerdistributed.repository.SchedulerRepository;
import com.robsonkades.springschedulerdistributed.repository.SubscriberRepository;

@Component
public class ExecuteJob {

    private final Logger logger = LoggerFactory.getLogger(ExecuteJob.class);

    private final SubscriberRepository repository;
    private final SchedulerRepository schedulerRepository;
    private final EventRepository eventRepository;

    public ExecuteJob(SubscriberRepository repository, SchedulerRepository schedulerRepository, EventRepository eventRepository) {
        this.repository = repository;
        this.schedulerRepository = schedulerRepository;
        this.eventRepository = eventRepository;
    }

    public void execute() {
        Optional<Scheduler> campaign = schedulerRepository.findFirst1ByNameOrderByPageDesc("CAMPAIGN_NAME");
        if (campaign.isEmpty()) {
            var scheduler = new Scheduler("CAMPAIGN_NAME", 0, 50);
            Page<Subscriber> subscribers = repository.findBy(PageRequest.of(scheduler.getPage(), scheduler.getLimit()));

            if (!subscribers.getContent().isEmpty()) {
                schedulerRepository.save(scheduler);
            }
            saveEvent("CAMPAIGN_NAME", subscribers.getContent());
            logger.info("initial {}", System.currentTimeMillis());
        } else {

            var scheduler = new Scheduler("CAMPAIGN_NAME", campaign.get().getPage() + 1, 50);
            Page<Subscriber> subscribers = repository.findBy(PageRequest.of(scheduler.getPage(), scheduler.getLimit()));

            if (!subscribers.getContent().isEmpty()) {
                schedulerRepository.save(scheduler);
                saveEvent("CAMPAIGN_NAME", subscribers.getContent());
            } else {
                //schedulerRepository.deleteAll();
                logger.info("end {}", System.currentTimeMillis());
            }
        }
    }

    public void saveEvent(String campaign, List<Subscriber> subscribers) {
        var all = subscribers
                .stream()
                .map(i -> new Event(campaign, i.getEmail()))
                .collect(Collectors.toList());
        eventRepository.saveAll(all);
    }
}
