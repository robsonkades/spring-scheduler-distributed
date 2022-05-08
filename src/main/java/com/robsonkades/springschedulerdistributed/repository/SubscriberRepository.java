package com.robsonkades.springschedulerdistributed.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.robsonkades.springschedulerdistributed.entity.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    List<Subscriber> findTop50By();

    Page<Subscriber> findBy(Pageable pageable);
}
