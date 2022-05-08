package com.robsonkades.springschedulerdistributed.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.robsonkades.springschedulerdistributed.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByCampaignAndEmail(String campaign, String email);
}
